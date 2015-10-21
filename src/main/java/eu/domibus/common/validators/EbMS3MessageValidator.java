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

package eu.domibus.common.validators;

import eu.domibus.common.exception.EbMS3Exception;
import eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.*;
import eu.domibus.ebms3.common.dao.PModeProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by kochc01 on 17.11.2014.
 */
public class EbMS3MessageValidator {
    private static final Log LOG = LogFactory.getLog(EbMS3MessageValidator.class);
    private static final String RFC2822_PATTERN_STRING = "^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$";
    private static final Pattern RFC_2822 = Pattern.compile(EbMS3MessageValidator.RFC2822_PATTERN_STRING);

    @Autowired
    private PModeProvider pModeProvider;

    @Qualifier("jaxbContextEBMS")
    @Autowired
    private JAXBContext ebmsContext;

    public void validate(final SOAPMessage message, final String pModeKey) throws EbMS3Exception {
        final Messaging messaging;
        try {
            messaging = this.ebmsContext.createUnmarshaller().unmarshal((Node) message.getSOAPHeader().getChildElements(ObjectFactory._Messaging_QNAME).next(), Messaging.class).getValue();
        } catch (JAXBException | SOAPException e) {
            EbMS3MessageValidator.LOG.error("", e);
            throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0004, null, e, null);
        }

        if (messaging.getUserMessage() == null && messaging.getSignalMessage() == null) { //There is no ebms message
            EbMS3MessageValidator.LOG.error("messaging element is empty");
            throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0004, null, null, null);
        }

        final UserMessage userMessage = messaging.getUserMessage();
        final SignalMessage signalMessage = messaging.getSignalMessage();

        if (userMessage != null) {
            this.validateUserMessage(userMessage, pModeKey);
        }
        if (signalMessage != null) {
            this.validateSignalMessage(signalMessage, pModeKey);
        }


    }

    private void validateSignalMessage(final SignalMessage signalMessage, final String pModeKey) {


    }

    private void validateUserMessage(final UserMessage userMessage, final String pModeKey) throws EbMS3Exception {
        final String mpc = userMessage.getMpc();
        //check if we know the mpc
        if (!this.pModeProvider.isMpcExistant(mpc)) {
            throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0001, "no mpc " + mpc + " found in configuration", null, null);
        }
        final MessageInfo messageInfo = userMessage.getMessageInfo();
        final Date timestamp = messageInfo.getTimestamp();
        if (timestamp == null) {
            throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0009, "required element eb:Messaging/eb:UserMessage/eb:Timestamp missing", null, null);
        }
        final String messageId = messageInfo.getMessageId();
        if (messageId == null) {
            throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0009, "required element eb:Messaging/eb:UserMessage/eb:MessageId missing", null, null);
        }
        if (!EbMS3MessageValidator.RFC_2822.matcher(messageId).matches()) {
            throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0009, "element eb:Messaging/eb:UserMessage/eb:MessageId does not conform to RFC2822 [CORE 5.2.2.1]", null, null);
        }


    }
}
