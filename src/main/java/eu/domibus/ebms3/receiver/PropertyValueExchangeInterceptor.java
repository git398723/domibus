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

import eu.domibus.ebms3.sender.MSHDispatcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * This interceptor is responsible for the exchange of parameters from a org.apache.cxf.binding.soap.SoapMessage to a javax.xml.soap.SOAPException
 */
public class PropertyValueExchangeInterceptor extends AbstractSoapInterceptor {

    private static final Log LOG = LogFactory.getLog(PropertyValueExchangeInterceptor.class);

    public PropertyValueExchangeInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {

        SOAPMessage jaxwsMessage = message.getContent(javax.xml.soap.SOAPMessage.class);
        try {
            jaxwsMessage.setProperty(MSHDispatcher.PMODE_KEY_CONTEXT_PROPERTY, message.getContextualProperty(MSHDispatcher.PMODE_KEY_CONTEXT_PROPERTY));

        } catch (SOAPException e) {
            PropertyValueExchangeInterceptor.LOG.error("", e);
        }
    }
}


