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

package eu.domibus.ebms3.receiver;

import eu.domibus.common.MSHRole;
import eu.domibus.common.dao.ErrorLogDao;
import eu.domibus.common.exception.EbMS3Exception;
import eu.domibus.common.model.logging.ErrorLogEntry;
import eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.Messaging;
import eu.domibus.ebms3.common.handler.AbstractFaultHandler;
import eu.domibus.ebms3.pmode.exception.NoMatchingPModeFoundException;
import eu.domibus.ebms3.sender.EbMS3MessageBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.ws.policy.PolicyException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.Set;

/**
 * This handler is resposible for creation of ebMS3 conformant error messages
 */
public class FaultInHandler extends AbstractFaultHandler {
    private static final Log LOG = LogFactory.getLog(FaultInHandler.class);

    @Autowired
    private EbMS3MessageBuilder messageBuilder;

    @Autowired
    private ErrorLogDao errorLogDao;


    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        //Do nothing as this is a fault handler
        return true;
    }

    @Override
    /**
     * The {@code handleFault} method is responsible for handling and conversion of exceptions
     * thrown during the processing of incoming ebMS3 messages
     */
    public boolean handleFault(SOAPMessageContext context) {

        Exception exception = (Exception) context.get(Exception.class.getName());
        Throwable cause = exception.getCause();
        EbMS3Exception ebMS3Exception = null;
        if (cause != null) {

            if (!(cause instanceof EbMS3Exception)) {
                //do Mapping of non ebms exceptions
                if (cause instanceof NoMatchingPModeFoundException) {
                    ebMS3Exception = new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0010, cause.getMessage(), ((NoMatchingPModeFoundException) cause).getMessageId(), cause, MSHRole.RECEIVING);
                } else {

                    if (cause instanceof WebServiceException) {
                        if (cause.getCause() instanceof EbMS3Exception) {
                            ebMS3Exception = (EbMS3Exception) cause.getCause();
                        }
                    } else {
                        ebMS3Exception = new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0004, "", cause, MSHRole.RECEIVING);
                    }
                }

            } else {
                ebMS3Exception = (EbMS3Exception) cause;
            }

            this.processEbMSError(context, ebMS3Exception);

        } else {
            if (exception instanceof PolicyException) {
                //FIXME: use a consistent way of property exchange between JAXWS and CXF message model. This: PhaseInterceptorChain
                String messageId = (String) PhaseInterceptorChain.getCurrentMessage().getContextualProperty("ebms.messageid");

                ebMS3Exception = new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0103, exception.getMessage(), messageId, exception, MSHRole.RECEIVING);
            } else {
                ebMS3Exception = new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0004, "", cause, MSHRole.RECEIVING);
            }

            this.processEbMSError(context, ebMS3Exception);
        }


        return true;
    }

    private void processEbMSError(SOAPMessageContext context, EbMS3Exception ebMS3Exception) {

        // at this point an EbMS3Exception is available in any case
        SOAPMessage soapMessageWithEbMS3Error = null;
        try {
            soapMessageWithEbMS3Error = this.messageBuilder.buildSOAPFaultMessage(ebMS3Exception.getFaultInfo());
        } catch (EbMS3Exception e) {
            this.errorLogDao.create(new ErrorLogEntry(e));
        }
        context.setMessage(soapMessageWithEbMS3Error);

        Messaging messaging = this.extractMessaging(soapMessageWithEbMS3Error);

        FaultInHandler.LOG.debug("An error occurred while receiving a message with ebMS3 messageId: " + messaging.getSignalMessage().getMessageInfo().getMessageId() + ". Please check the database for more detailed information.", ebMS3Exception);

        this.errorLogDao.create(ErrorLogEntry.parse(messaging, MSHRole.RECEIVING));
    }

    @Override
    public void close(MessageContext context) {

    }
}
