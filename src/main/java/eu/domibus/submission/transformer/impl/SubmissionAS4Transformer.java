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

package eu.domibus.submission.transformer.impl;

import eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.*;
import eu.domibus.ebms3.common.MessageIdGenerator;
import eu.domibus.submission.Submission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * TODO: add class description
 */
@org.springframework.stereotype.Service
public class SubmissionAS4Transformer {

    public static final String DESCRIPTION_PROPERTY_NAME = "description";

    @Autowired
    private MessageIdGenerator messageIdGenerator;

    public UserMessage transformFromSubmission(final Submission submission) {
        final UserMessage result = new UserMessage();
        this.generateCollaborationInfo(submission, result);
        this.generateMessageInfo(submission, result);
        this.generatePartyInfo(submission, result);
        this.generatePayload(submission, result);
        this.generateMessageProperties(submission, result);

        //TODO: set mpc from pmode

        return result;
    }

    private void generateMessageProperties(final Submission submission, final UserMessage result) {

        final MessageProperties messageProperties = new MessageProperties();


        for (final Map.Entry<Object, Object> propertyEntry : submission.getMessageProperties().entrySet()) {
            final Property prop = new Property();
            prop.setName(propertyEntry.getKey().toString());
            prop.setValue(propertyEntry.getValue().toString());
            messageProperties.getProperty().add(prop);
        }

        result.setMessageProperties(messageProperties);
    }

    private void generateCollaborationInfo(final Submission submission, final UserMessage result) {
        final CollaborationInfo collaborationInfo = new CollaborationInfo();
        collaborationInfo.setConversationId((submission.getConversationId() != null && submission.getConversationId().trim().length() > 0) ? submission.getConversationId() : this.generateConversationId());
        collaborationInfo.setAction(submission.getAction());
        final AgreementRef agreementRef = new AgreementRef();
        agreementRef.setValue(submission.getAgreementRef());
        agreementRef.setType(submission.getAgreementRefType());
        collaborationInfo.setAgreementRef(agreementRef);
        final Service service = new Service();
        service.setValue(submission.getService());
        service.setType(submission.getServiceType());
        collaborationInfo.setService(service);
        result.setCollaborationInfo(collaborationInfo);
    }

    private void generateMessageInfo(final Submission submission, final UserMessage result) {
        final MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessageId((submission.getMessageId() != null && submission.getMessageId().trim().length() > 0) ? submission.getMessageId() : this.messageIdGenerator.generateMessageId());
        messageInfo.setTimestamp(new Date());
        messageInfo.setRefToMessageId(submission.getRefToMessageId());
        result.setMessageInfo(messageInfo);
    }

    private void generatePartyInfo(final Submission submission, final UserMessage result) {
        final PartyInfo partyInfo = new PartyInfo();
        final From from = new From();
        from.setRole(submission.getFromRole());
        for (final Submission.Party party : submission.getFromParties()) {
            final PartyId partyId = new PartyId();
            partyId.setValue(party.getPartyId());
            partyId.setType(party.getPartyIdType());
            from.getPartyId().add(partyId);
        }
        partyInfo.setFrom(from);

        final To to = new To();
        to.setRole(submission.getToRole());
        for (final Submission.Party party : submission.getToParties()) {
            final PartyId partyId = new PartyId();
            partyId.setValue(party.getPartyId());
            partyId.setType(party.getPartyIdType());
            to.getPartyId().add(partyId);
        }
        partyInfo.setTo(to);

        result.setPartyInfo(partyInfo);
    }


    private void generatePayload(final Submission submission, final UserMessage result) {
        final PayloadInfo payloadInfo = new PayloadInfo();


        for (final Submission.Payload payload : submission.getPayloads()) {
            final PartInfo partInfo = new PartInfo();
            partInfo.setInBody(payload.isInBody());
            partInfo.setBinaryData(payload.getPayloadData());
            partInfo.setHref(payload.getContentId());
            final Schema schema = new Schema();
            schema.setLocation(payload.getSchemaLocation());
            partInfo.setSchema(schema);
            boolean descriptionPropertyExists = false;
            final PartProperties partProperties = new PartProperties();
            for (final Map.Entry<Object, Object> entry : payload.getPayloadProperties().entrySet()) {
                final Property property = new Property();
                property.setName(entry.getKey().toString());
                property.setValue(entry.getValue().toString());
                partProperties.getProperties().add(property);
                if (DESCRIPTION_PROPERTY_NAME.equals(property.getName())) {
                    descriptionPropertyExists = true;
                }
            }

            if (descriptionPropertyExists) {
                final Description description = new Description();
                description.setValue(payload.getDescription());
                partInfo.setDescription(description);
            } else {
                Property descriptionProperty = new Property();
                descriptionProperty.setName(DESCRIPTION_PROPERTY_NAME);
                descriptionProperty.setValue(payload.getDescription());
                partProperties.getProperties().add(descriptionProperty);
            }
            partInfo.setPartProperties(partProperties);
            payloadInfo.getPartInfo().add(partInfo);

            result.setPayloadInfo(payloadInfo);
        }


    }

    public Submission transformFromMessaging(final UserMessage messaging) {
        final Submission result = new Submission();

        final CollaborationInfo collaborationInfo = messaging.getCollaborationInfo();
        result.setAction(collaborationInfo.getAction());
        result.setService(messaging.getCollaborationInfo().getService().getValue());
        result.setServiceType(messaging.getCollaborationInfo().getService().getType());
        if (collaborationInfo.getAgreementRef() != null) {
            result.setAgreementRef(collaborationInfo.getAgreementRef().getValue());
            result.setAgreementRefType(collaborationInfo.getAgreementRef().getType());
        }
        result.setConversationId(collaborationInfo.getConversationId());

        result.setMessageId(messaging.getMessageInfo().getMessageId());
        result.setRefToMessageId(messaging.getMessageInfo().getRefToMessageId());

        if (messaging.getPayloadInfo() != null) {
            for (final PartInfo partInfo : messaging.getPayloadInfo().getPartInfo()) {
                final Properties properties = new Properties();
                if (partInfo.getPartProperties() != null) {
                    for (final Property property : partInfo.getPartProperties().getProperties()) {
                        properties.setProperty(property.getName(), property.getValue());
                    }
                }

                result.addPayload(partInfo.getHref(), partInfo.getBinaryData(), properties, partInfo.isInBody(), (partInfo.getDescription() != null) ? partInfo.getDescription().getValue() : null, (partInfo.getSchema() != null) ? partInfo.getSchema().getLocation() : null);
            }
        }
        result.setFromRole(messaging.getPartyInfo().getFrom().getRole());
        result.setToRole(messaging.getPartyInfo().getTo().getRole());

        for (final PartyId partyId : messaging.getPartyInfo().getFrom().getPartyId()) {
            result.addFromParty(partyId.getValue(), partyId.getType());
        }

        for (final PartyId partyId : messaging.getPartyInfo().getTo().getPartyId()) {
            result.addToParty(partyId.getValue(), partyId.getType());
        }

        if (messaging.getMessageProperties() != null) {
            for (final Property property : messaging.getMessageProperties().getProperty()) {
                result.addMessageProperty(property.getName(), property.getValue());
            }
        }

        return result;
    }


    private String generateConversationId() {
        return this.messageIdGenerator.generateMessageId();
    }
}
