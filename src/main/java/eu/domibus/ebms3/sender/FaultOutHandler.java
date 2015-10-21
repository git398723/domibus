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

import eu.domibus.common.MSHRole;
import eu.domibus.common.dao.ErrorLogDao;
import eu.domibus.common.model.logging.ErrorLogEntry;
import eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.Messaging;
import eu.domibus.ebms3.common.handler.AbstractFaultHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.Set;

/**
 * This handler is responsible for processing of incoming ebMS3 errors as a response of an outgoing ebMS3 message.
 */
public class FaultOutHandler extends AbstractFaultHandler {

    private static final Log LOG = LogFactory.getLog(FaultOutHandler.class);

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


    /**
     * The {@code handleFault} method is responsible for logging of incoming ebMS3 errors
     */
    @Override
    public boolean handleFault(SOAPMessageContext context) {

        Messaging messaging = this.extractMessaging(context.getMessage());

        FaultOutHandler.LOG.debug("An ebMS3 error was received for message with ebMS3 messageId:" + messaging.getSignalMessage().getMessageInfo().getMessageId() + ". Please check the database for more detailed information.");
        this.errorLogDao.create(ErrorLogEntry.parse(messaging, MSHRole.SENDING));

        return true;
    }

    @Override
    public void close(MessageContext context) {

    }
}
