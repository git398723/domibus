/*
 * Copyright 2015 e-CODEX Project
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 * http://ec.europa.eu/idabc/eupl5
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package eu.domibus.ebms3.sender;

//import eu.domibus.ebms3.pmode.model.PMode;

import eu.domibus.common.configuration.model.LegConfiguration;
import eu.domibus.common.exception.EbMS3Exception;
import eu.domibus.ebms3.common.dao.PModeProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.attachment.AttachmentImpl;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.policy.PolicyBuilder;
import org.apache.cxf.ws.policy.PolicyConstants;
import org.apache.cxf.ws.policy.PolicyInInterceptor;
import org.apache.cxf.ws.policy.PolicyVerificationOutInterceptor;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.wsdl.interceptors.BareOutInterceptor;
import org.apache.neethi.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This interceptor is responsible for discovery and setup of WS-Security Policies for outgoing messages
 *
 * @author Christian Koch
 * @since 3.0
 */
public class SetPolicyOutInterceptor extends AbstractSoapInterceptor {
    private static final Log LOG = LogFactory.getLog(SetPolicyOutInterceptor.class);

    @Autowired
    private PModeProvider pModeProvider;


    public SetPolicyOutInterceptor() {
        super(Phase.SETUP);
        this.addBefore(PolicyInInterceptor.class.getName());
    }

    /**
     * Intercepts a message.
     * Interceptors should NOT invoke handleMessage or handleFault
     * on the next interceptor - the interceptor chain will
     * take care of this.
     *
     * @param message the message to handle
     */
    @Override
    public void handleMessage(SoapMessage message) throws Fault {


        String pModeKey = (String) message.getContextualProperty(MSHDispatcher.PMODE_KEY_CONTEXT_PROPERTY);
        PolicyBuilder builder = message.getExchange().getBus().getExtension(PolicyBuilder.class);
        message.getInterceptorChain().add(new SetPolicyOutInterceptor.PrepareAttachmentInterceptor());

        LegConfiguration legConfiguration = this.pModeProvider.getLegConfiguration(pModeKey);


        message.put(SecurityConstants.ASYMMETRIC_SIGNATURE_ALGORITHM, legConfiguration.getSecurity().getSignatureMethod().getAlgorithm());
        message.put(SecurityConstants.ENCRYPT_USERNAME, this.pModeProvider.getReceiverParty(pModeKey).getName());

        try {

            Policy policy = builder.getPolicy(new FileInputStream(new File(System.getProperty("domibus.config.location"), legConfiguration.getSecurity().getPolicy())));
            message.put(PolicyConstants.POLICY_OVERRIDE, policy);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            SetPolicyOutInterceptor.LOG.error("", e);
            throw new Fault(new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0004, "Could not find policy file " + System.getProperty("domibus.config.location") + "/" + this.pModeProvider.getLegConfiguration(pModeKey).getSecurity(), null, null));
        }

    }

    static class PrepareAttachmentInterceptor extends AbstractPhaseInterceptor<Message> {
        public PrepareAttachmentInterceptor() {
            super(Phase.MARSHAL);
            super.addBefore(BareOutInterceptor.class.getName());
        }

        /**
         * Intercepts a message.
         * Interceptors should NOT invoke handleMessage or handleFault
         * on the next interceptor - the interceptor chain will
         * take care of this.
         *
         * @param message message to handle
         */
        @Override
        public void handleMessage(Message message) throws Fault {
            /*InterceptorChain interceptorChain = message.getInterceptorChain();
            Interceptor wss4jInternal = null;
            PolicyBasedWSS4JOutInterceptor wss4JOutInterceptor = null;

            for(Interceptor interceptor : interceptorChain){
                LOG.info(interceptor.getClass().getName());
            }

            for(Interceptor interceptor : interceptorChain){
                if(interceptor.getClass().getName().contains("WSS4JOutInterceptorInternal")){
                    wss4jInternal = interceptor;
                }
                if (interceptor instanceof PolicyBasedWSS4JOutInterceptor){
                    wss4JOutInterceptor = (PolicyBasedWSS4JOutInterceptor)interceptor;
                }

            }
            interceptorChain.remove(wss4jInternal);
            interceptorChain.add(wss4JOutInterceptor.createEndingInterceptor());

           for(Interceptor interceptor : interceptorChain){
                LOG.info(interceptor.getClass().getName());
            }*/

            SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
            if (soapMessage.countAttachments() > 0) {
                if (message.getAttachments() == null) {
                    message.setAttachments(new ArrayList<Attachment>(soapMessage
                            .countAttachments()));
                }
                Iterator<AttachmentPart> it = CastUtils.cast(soapMessage.getAttachments());
                while (it.hasNext()) {
                    AttachmentPart part = it.next();
                    String id = AttachmentUtil.cleanContentId(part.getContentId());
                    AttachmentImpl att = new AttachmentImpl(id);
                    try {
                        att.setDataHandler(part.getDataHandler());
                    } catch (SOAPException e) {
                        throw new Fault(e);
                    }
                    Iterator<MimeHeader> it2 = CastUtils.cast(part.getAllMimeHeaders());
                    while (it2.hasNext()) {
                        MimeHeader header = it2.next();
                        att.setHeader(header.getName(), header.getValue());
                    }
                    message.getAttachments().add(att);
                }
            }
            message.getInterceptorChain().add(new SetPolicyOutInterceptor.LogAfterPolicyCheckInterceptor());


        }

    }

    public static class LogAfterPolicyCheckInterceptor extends AbstractSoapInterceptor {


        public LogAfterPolicyCheckInterceptor() {
            super(Phase.POST_STREAM);
            this.addAfter(PolicyVerificationOutInterceptor.class.getName());
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {

            SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
            soapMessage.removeAllAttachments();
        }
    }
}
