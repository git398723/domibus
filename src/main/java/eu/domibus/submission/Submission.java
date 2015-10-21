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

package eu.domibus.submission;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * This class represents the datamodel of the domibus backend transport layer.
 * <p/>
 * In order to simplify the process of transforming data from/to the backend this
 * datamodel was introduced. A transformer for mapping this model into the complex
 * ebMS3 datamodel is already implemented ({@link eu.domibus.submission.transformer.impl.SubmissionAS4Transformer}).
 * Therefore for a new backend implementation it is only required to implement a simple transformation from/to the
 * backend model and this simple datamodel.
 */
public class Submission {

    private final Set<Submission.Party> fromParties = new HashSet<>();
    private final Set<Submission.Party> toParties = new HashSet<>();
    //TODO: MessageProperties have an optional attribute type which is not covered yet
    private final Properties messageProperties = new Properties();
    private final Set<Submission.Payload> payloads = new HashSet<>();
    private String action;
    private String service;
    private String serviceType;
    private String conversationId;
    private String messageId;
    private String refToMessageId;
    private String agreementRef;
    private String agreementRefType;
    private String fromRole;
    private String toRole;

    /**
     * Getter for action
     * <p/>
     * "This element is a string identifying an operation or an activity within a Service. Its actual semantics is
     * beyond the scope of this specification. Action SHALL be unique within the Service in which it is defined.
     * The value of the Action element is specified by the designer of the service."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return a string identifying an operation or an activity within a Service that may support several of these.
     */
    public String getAction() {
        return this.action;
    }

    /**
     * Setter for action
     * <p/>
     * "This element is a string identifying an operation or an activity within a Service. Its actual semantics is
     * beyond the scope of this specification. Action SHALL be unique within the Service in which it is defined.
     * The value of the Action element is specified by the designer of the service."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param action a string identifying an operation or an activity within a Service that may support several of these.
     */
    public void setAction(final String action) {
        this.action = action;
    }

    /**
     * Getter for agreementref
     * <p/>
     * "AgreementRef is a string value that identifies the agreement that governs the exchange. The P-Mode
     * under which the MSH operates for this message should be aligned with this agreement.
     * The value of an AgreementRef element MUST be unique within a namespace mutually agreed by the two
     * parties. This could be a concatenation of the From and To PartyId values, a URI containing the Internet
     * domain name of one of the parties, or a namespace offered and managed by some other naming or
     * registry service. It is RECOMMENDED that the AgreementRef be a URI."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return a string that identifies the entity or artifact governing the exchange of messages between the parties.
     */
    public String getAgreementRef() {
        return this.agreementRef;
    }

    /**
     * Setter for agreementref
     * <p/>
     * "AgreementRef is a string value that identifies the agreement that governs the exchange. The P-Mode
     * under which the MSH operates for this message should be aligned with this agreement.
     * The value of an AgreementRef element MUST be unique within a namespace mutually agreed by the two
     * parties. This could be a concatenation of the From and To PartyId values, a URI containing the Internet
     * domain name of one of the parties, or a namespace offered and managed by some other naming or
     * registry service. It is RECOMMENDED that the AgreementRef be a URI."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param agreementRef a string that identifies the entity or artifact governing the exchange of messages between the parties.
     */
    public void setAgreementRef(final String agreementRef) {
        this.agreementRef = agreementRef;
    }

    /**
     * Getter for agreementRefType
     * <p/>
     * "This OPTIONAL attribute indicates how the parties sending and receiving the message will interpret the value of
     * the reference (e.g. the value could be "ebcppa2.1" for parties using a CPA-based agreement representation). There
     * is no restriction on the value of the type attribute; this choice is left to profiles of this specification. If
     * the type attribute is not present, the content of the eb:AgreementRef element MUST be a URI."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return a string indicating how the parties sending and receiving the message will interpret the value of the reference {@link #agreementRef}
     */
    public String getAgreementRefType() {
        return this.agreementRefType;
    }

    /**
     * Setter for agreementRefType
     * <p/>
     * "This OPTIONAL attribute indicates how the parties sending and receiving the message will interpret the value of
     * the reference (e.g. the value could be "ebcppa2.1" for parties using a CPA-based agreement representation). There
     * is no restriction on the value of the type attribute; this choice is left to profiles of this specification. If
     * the type attribute is not present, the content of the eb:AgreementRef element MUST be a URI."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param agreementRefType a string indicating how the parties sending and receiving the message will interpret the value of the reference {@link #agreementRef}
     */
    public void setAgreementRefType(final String agreementRefType) {
        this.agreementRefType = agreementRefType;
    }

    /**
     * Getter for conversationId
     * <p/>
     * "The Party initiating a conversation determines the value of the ConversationId element that SHALL be
     * reflected in all messages pertaining to that conversation. The actual semantics of this value is beyond the
     * scope of this specification. Implementations SHOULD provide a facility for mapping between their
     * identification scheme and a ConversationId generated by another implementation."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return a string identifying the set of related messages that make up a conversation between Parties
     */
    public String getConversationId() {
        return this.conversationId;
    }

    /**
     * Setter for conversationId
     * If not set a random value will be generated.
     * <p/>
     * "The Party initiating a conversation determines the value of the ConversationId element that SHALL be
     * reflected in all messages pertaining to that conversation. The actual semantics of this value is beyond the
     * scope of this specification. Implementations SHOULD provide a facility for mapping between their
     * identification scheme and a ConversationId generated by another implementation."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param conversationId a string identifying the set of related messages that make up a conversation between Parties
     */
    public void setConversationId(final String conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * Getter for fromRole
     * <p/>
     * "The REQUIRED eb:Role element occurs once, and identifies the authorized role
     * (fromAuthorizedRole or toAuthorizedRole) of the Party sending (when present as a child of the From element) or
     * receiving (when present as a child of the To element) the message. The value of the Role element is a non- empty
     * string, with a default value of http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultRole. Other
     * possible values are subject to partner agreement."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return a string identifying the authorized role of the Party sending
     */
    public String getFromRole() {
        return this.fromRole;
    }

    /**
     * Setter for fromRole
     * <p/>
     * "The REQUIRED eb:Role element occurs once, and identifies the authorized role
     * (fromAuthorizedRole or toAuthorizedRole) of the Party sending (when present as a child of the From element) or
     * receiving (when present as a child of the To element) the message. The value of the Role element is a non- empty
     * string, with a default value of http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultRole. Other
     * possible values are subject to partner agreement."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param fromRole a string identifying the authorized role of the Party sending
     */
    public void setFromRole(final String fromRole) {
        this.fromRole = fromRole;
    }

    /**
     * Getter for messageId
     * <p/>
     * "This REQUIRED element has a value representing – for each message - a globally unique identifier conforming to
     * MessageId [RFC2822]. Note: In the Message-Id and Content-Id MIME headers, values are always surrounded by angle
     * brackets. However references in mid: or cid: scheme URI's and the MessageId and RefToMessageId elements MUST NOT
     * include these delimiters."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return a string representing a globally unique identifier conforming to MessageId [RFC2822]
     */
    public String getMessageId() {
        return this.messageId;
    }

    /**
     * Setter for messageId
     * The prefix added to the messageId, in case of sending from domibus, is configurable
     * <p/>
     * "This REQUIRED element has a value representing – for each message - a globally unique identifier conforming to
     * MessageId [RFC2822]. Note: In the Message-Id and Content-Id MIME headers, values are always surrounded by angle
     * brackets. However references in mid: or cid: scheme URI's and the MessageId and RefToMessageId elements MUST NOT
     * include these delimiters."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param messageId a string representing a globally unique identifier conforming to MessageId [RFC2822]
     */
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    /**
     * Getter for refToMessageId
     * <p/>
     * "This OPTIONAL element occurs at most once. When present, it MUST contain the MessageId value of an ebMS Message
     * to which this message relates, in a way that conforms to the MEP in use."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return a string containing the {@link #messageId} value of an ebMS Message to which this messages relates
     */
    public String getRefToMessageId() {
        return this.refToMessageId;
    }

    /**
     * Setter for refToMessageId
     * <p/>
     * "This OPTIONAL element occurs at most once. When present, it MUST contain the MessageId value of an ebMS Message
     * to which this message relates, in a way that conforms to the MEP in use."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param refToMessageId a string containing the {@link #messageId} value of an ebMS Message to which this messages relates
     */
    public void setRefToMessageId(final String refToMessageId) {
        this.refToMessageId = refToMessageId;
    }

    /**
     * Getter for service
     * <p/>
     * "This element identifies the service that acts on the message. Its actual semantics is beyond the scope of
     * this specification. The designer of the service may be a standards organization, or an individual or
     * enterprise."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return a string identifying the service that acts on the message
     */
    public String getService() {
        return this.service;
    }

    /**
     * Setter for service
     * <p/>
     * "This element identifies the service that acts on the message. Its actual semantics is beyond the scope of
     * this specification. The designer of the service may be a standards organization, or an individual or
     * enterprise."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param service a string identifying the service that acts on the message
     */
    public void setService(final String service) {
        this.service = service;
    }

    /**
     * Getter for serviceType
     * <p/>
     * "The Service element MAY contain a single @type attribute, that indicates how the parties sending and
     * receiving the message will interpret the value of the element. There is no restriction on the value of the
     * type attribute. If the type attribute is not present, the content of the Service element MUST be a URI."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return a string that indicates how the parties sending and receiving the message will interpret the {@link #service} value
     */
    public String getServiceType() {
        return this.serviceType;
    }

    /**
     * Setter serviceType
     * <p/>
     * "The Service element MAY contain a single @type attribute, that indicates how the parties sending and
     * receiving the message will interpret the value of the element. There is no restriction on the value of the
     * type attribute. If the type attribute is not present, the content of the Service element MUST be a URI."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param serviceType a string that indicates how the parties sending and receiving the message will interpret the {@link #service} value
     */
    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * Getter toRole
     * <p/>
     * "The REQUIRED eb:Role element occurs once, and identifies the authorized role
     * (fromAuthorizedRole or toAuthorizedRole) of the Party sending (when present as a child of the From element) or
     * receiving (when present as a child of the To element) the message. The value of the Role element is a non- empty
     * string, with a default value of http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultRole. Other
     * possible values are subject to partner agreement."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return a string identifying the authorized role of the receiving Party
     */
    public String getToRole() {
        return this.toRole;
    }

    /**
     * Setter toRole
     * <p/>
     * "The REQUIRED eb:Role element occurs once, and identifies the authorized role
     * (fromAuthorizedRole or toAuthorizedRole) of the Party sending (when present as a child of the From element) or
     * receiving (when present as a child of the To element) the message. The value of the Role element is a non- empty
     * string, with a default value of http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultRole. Other
     * possible values are subject to partner agreement."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param toRole a string identifying the authorized role of the receiving Party
     */
    public void setToRole(final String toRole) {
        this.toRole = toRole;
    }

    /**
     * Returns a {@link java.util.Set} of {@link eu.domibus.submission.Submission.Party} elements representing the fromParties
     * of this submission. A fromParty contains information describing the originating party.
     *
     * @return a {@link java.util.Set} of {@link eu.domibus.submission.Submission.Party} elements containing information describing the originating party
     */
    public Set<Submission.Party> getFromParties() {
        return this.fromParties;
    }

    /**
     * This method adds one message property to the submission. The optional type attribute is not set.
     * <p/>
     * "Its actual semantics is beyond the scope of this specification. The element is intended to be consumed outside
     * the ebMS-specified functions. It may contain some information that qualifies or abstracts message data, or that
     * allows for binding the message to some business process. A representation in the header of such properties allows
     * for more efficient monitoring, correlating, dispatching and validating functions (even if these are out of scope
     * of ebMS specification) that do not require payload access."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param name  a string representing the name of the message property
     * @param value a string representing the value of the message property
     */
    public void addMessageProperty(final String name, final String value) {
        this.messageProperties.setProperty(name, value);
    }

    /**
     * This method adds one message property to the submission.
     * <p/>
     * "Its actual semantics is beyond the scope of this specification. The element is intended to be consumed outside
     * the ebMS-specified functions. It may contain some information that qualifies or abstracts message data, or that
     * allows for binding the message to some business process. A representation in the header of such properties allows
     * for more efficient monitoring, correlating, dispatching and validating functions (even if these are out of scope
     * of ebMS specification) that do not require payload access."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param name  a string representing the name of the message property
     * @param type  a string representing the type of the message property
     * @param value a string representing the value of the message property
     */
    public void addMessageProperty(final String name, final String type, final String value) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    /**
     * Getter for all message properties of this submission.
     * <p/>
     * "Its actual semantics is beyond the scope of this specification. The element is intended to be consumed outside
     * the ebMS-specified functions. It may contain some information that qualifies or abstracts message data, or that
     * allows for binding the message to some business process. A representation in the header of such properties allows
     * for more efficient monitoring, correlating, dispatching and validating functions (even if these are out of scope
     * of ebMS specification) that do not require payload access."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @return an object of type {@link java.util.Properties} representing the message properties of this submission
     */
    public Properties getMessageProperties() {
        return this.messageProperties;
    }

    /**
     * Returns a {@link java.util.Set} of {@link eu.domibus.submission.Submission.Party} elements representing the toParties
     * of this submission. A toParty contains information describing the destination party.
     *
     * @return a {@link java.util.Set} of {@link eu.domibus.submission.Submission.Party} elements containing information describing the destination party.
     */
    public Set<Submission.Party> getToParties() {
        return this.toParties;
    }

    /**
     * Returns a {@link java.util.Set} of {@link eu.domibus.submission.Submission.Payload} elements representing the payloads
     * of this submission. A {@link eu.domibus.submission.Submission.Payload} contains information describing the payload and the payload itself.
     *
     * @return a {@link java.util.Set} of {@link eu.domibus.submission.Submission.Payload} elements representing the payloads of this submission
     */
    public Set<Submission.Payload> getPayloads() {
        return this.payloads;
    }

    /**
     * This method adds one payload to the {@link java.util.Set} of {@link eu.domibus.submission.Submission.Payload} elements.
     * In this case the parameter contentId and the payload data itself were set. By default a payload added with this method
     * is considered to be NOT part of the soap body.
     *
     * @param contentId   a string that is the [RFC2392] Content-ID URI of the payload object referenced
     * @param payloadData a byte array with the payload data
     */
    public void addPayload(final String contentId, final byte[] payloadData) {
        this.addPayload(contentId, payloadData, null, false, null, null);
    }

    /**
     * This method adds one payload to the {@link java.util.Set} of {@link eu.domibus.submission.Submission.Payload} elements.
     * In this case the parameter contentId, a {@link java.util.Set} of payload properties and the payload data itself were set.
     * By default a payload added with this method is considered to be NOT part of the soap body.
     * <p/>
     * Payload properties will be mapped into eb:PartProperties in ebMS described here:
     * "This element has zero or more eb:Property child elements. An eb:Property element is of xs:anySimpleType
     * (e.g. string, URI) and has a REQUIRED @name attribute, the value of which must be agreed between partners. Its
     * actual semantics is beyond the scope of this specification. The element is intended to be consumed outside the
     * ebMS specified functions. It may contain meta-data that qualifies or abstracts the payload data. A representation
     * in the header of such properties allows for more efficient monitoring, correlating, dispatching and validating
     * functions (even if these are out of scope of ebMS specification) that do not require payload access."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)
     *
     * @param contentId         a string that is the [RFC2392] Content-ID URI of the payload object referenced
     * @param payloadData       a byte array with the payload data
     * @param payloadProperties a {@link java.util.Properties} object representing the properties of this payload
     */
    public void addPayload(final String contentId, final byte[] payloadData, final Properties payloadProperties) {
        this.addPayload(contentId, payloadData, payloadProperties, false, null, null);
    }

    /**
     * This method adds one payload to the {@link java.util.Set} of {@link eu.domibus.submission.Submission.Payload} elements.
     * In this case it is possible to set all parameters available for a payload.
     * <p/>
     * <ol>
     * <li><b>contentId:</b> Will be mapped the href attribute of the eb:partInfo element in ebMS described here:
     * This OPTIONAL attribute has a value that is the [RFC2392] Content-ID URI of the payload object
     * referenced, an xml:id fragment identifier, or the URL of an externally referenced resource; for example,
     * "cid:foo@example.com" or "#idref". The absence of the attribute href in the element eb:PartInfo indicates
     * that the payload part being referenced is the SOAP Body element itself. For example, a declaration of the
     * following form simply indicates that the entire SOAP Body is to be considered a payload part in this ebMS
     * message.</li>
     * <li><b>payloadData:</b> The payload data itself.</li>
     * <li><b>payloadProperties:</b> Payload properties will be mapped into eb:PartProperties in ebMS described here:
     * "This element has zero or more eb:Property child elements. An eb:Property element is of xs:anySimpleType
     * (e.g. string, URI) and has a REQUIRED @name attribute, the value of which must be agreed between partners. Its
     * actual semantics is beyond the scope of this specification. The element is intended to be consumed outside the
     * ebMS specified functions. It may contain meta-data that qualifies or abstracts the payload data. A representation
     * in the header of such properties allows for more efficient monitoring, correlating, dispatching and validating
     * functions (even if these are out of scope of ebMS specification) that do not require payload access."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)</li>
     * <li><b>inBody:</b> if {@code true} the payload will be the content of the soap body.
     * If {@code false} it will be handled as an attachment of the soap message according to SwA specification.</li>
     * <li><b>description:</b> a description property. Will be mapped into a eb:Description element in ebMS</li>
     * <li><b>schemalocation:</b> "URI of schema(s) that define the instance document identified in the parent PartInfo
     * element. If the item being referenced has schema(s) of some kind that describe it (e.g. an XML Schema, DTD
     * and/or a database schema), then the Schema element SHOULD be present as a child of the PartInfo element.
     * It provides a means of identifying the schema and its version defining the payload object identified by the
     * parent PartInfo element. This metadata MAY be used to validate the Payload Part to which it refers, but the
     * MSH is NOT REQUIRED to do so ..."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)</li>
     * </ol>
     *
     * @param contentId         a string that is the [RFC2392] Content-ID URI of the payload object referenced
     * @param payloadData       a byte array with the payload data
     * @param payloadProperties a {@link java.util.Properties} object representing the properties of this payload
     * @param inBody            if {@code true} the payload will be the content of the soap body.If {@code false} it will be handled as an attachment of the soap message according to SwA specification
     * @param description       a string representing a description property
     * @param schemaLocation    a string representing a URI of schema(s) that define the instance document
     */
    public void addPayload(final String contentId, final byte[] payloadData, final Properties payloadProperties, final boolean inBody, final String description, final String schemaLocation) {
        this.payloads.add(new Submission.Payload(contentId, payloadData, payloadProperties, inBody, description, schemaLocation));
    }

    /**
     * This method adds one originating party to the {@link java.util.Set} of {@link eu.domibus.submission.Submission.Party} elements.
     * <p/>
     * Paramters are:
     * <ol>
     * <li><b>partyId:</b> This is mapped into eb:From/eb:PartyId in ebMS described here:
     * "This element has a string value content that identifies a party, or that is one of the identifiers of this party."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)</li>
     * <li><b>partyIdType:</b> This is mapped into eb:From/eb:PartyId[@type] in ebMS descibed here:
     * "... The type attribute indicates the domain of names to which the string in the content of the PartyId
     * element belongs. It is RECOMMENDED that the value of the type attribute be a URI. It is further RECOMMENDED
     * that these values be taken from the EDIRA , EDIFACT or ANSI ASC X12 registries. Technical specifications for
     * the first two registries can be found at and [ISO6523] and [ISO9735], respectively. Further discussion of
     * PartyId types and methods of construction can be found in an appendix of [ebCPPA21]. The value of any
     * given @type attribute MUST be unique within a list of PartyId elements."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)</li>
     * </ol>
     *
     * @param partyId     a string that identifies the originating party
     * @param partyIdType a string that identifies the type of the partyId
     */
    public void addFromParty(final String partyId, final String partyIdType) {
        this.fromParties.add(new Submission.Party(partyId, partyIdType));
    }

    /**
     * This method adds one destination party to the {@link java.util.Set} of {@link eu.domibus.submission.Submission.Party} elements.
     * <p/>
     * Paramters are:
     * <ol>
     * <li><b>partyId:</b> This is mapped into eb:To/eb:PartyId in ebMS described here:
     * "This element has a string value content that identifies a party, or that is one of the identifiers of this party."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)</li>
     * <li><b>partyIdType:</b> This is mapped into eb:To/eb:PartyId[@type] in ebMS descibed here:
     * "... The type attribute indicates the domain of names to which the string in the content of the PartyId
     * element belongs. It is RECOMMENDED that the value of the type attribute be a URI. It is further RECOMMENDED
     * that these values be taken from the EDIRA , EDIFACT or ANSI ASC X12 registries. Technical specifications for
     * the first two registries can be found at and [ISO6523] and [ISO9735], respectively. Further discussion of
     * PartyId types and methods of construction can be found in an appendix of [ebCPPA21]. The value of any
     * given @type attribute MUST be unique within a list of PartyId elements."
     * (OASIS ebXML Messaging Services Version 3.0: Part 1, Core Features, 1 October 2007)</li>
     * </ol>
     *
     * @param partyId     a string that identifies the destination party
     * @param partyIdType a string that identifies the type of the partyId
     */
    public void addToParty(final String partyId, final String partyIdType) {
        this.toParties.add(new Submission.Party(partyId, partyIdType));
    }

    public static class Party {
        private final String partyId;
        private final String partyIdType;

        Party(final String partyId, final String partyIdType) {
            this.partyId = partyId;
            this.partyIdType = partyIdType;
        }

        public String getPartyId() {
            return this.partyId;
        }

        public String getPartyIdType() {
            return this.partyIdType;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof Submission.Party)) return false;

            final Submission.Party party = (Submission.Party) o;

            if (!this.partyId.equals(party.partyId)) return false;
            return !(this.partyIdType != null ? !this.partyIdType.equals(party.partyIdType) : party.partyIdType != null);

        }

        @Override
        public int hashCode() {
            int result = this.partyId.hashCode();
            result = 31 * result + (this.partyIdType != null ? this.partyIdType.hashCode() : 0);
            return result;
        }
    }

    public static class Payload {
        //TODO: Validation not supported yet
        private final String schemaLocation;
        private final String contentId;
        private final String description;
        private final byte[] payloadData;
        private final Properties payloadProperties;
        private final boolean inBody;

        Payload(final String contentId, final byte[] payloadData, final Properties payloadProperties, final boolean inBody, final String description, final String schemaLocation) {
            this.contentId = contentId;
            this.payloadData = payloadData;
            this.description = description;
            this.payloadProperties = payloadProperties;
            this.inBody = inBody;
            this.schemaLocation = schemaLocation;
        }

        public String getContentId() {
            return this.contentId;
        }

        public byte[] getPayloadData() {
            return this.payloadData;
        }

        public Properties getPayloadProperties() {
            return this.payloadProperties;
        }

        public String getSchemaLocation() {
            return this.schemaLocation;
        }

        public boolean isInBody() {
            return this.inBody;
        }

        public String getDescription() {
            return this.description;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof Submission.Payload)) return false;

            final Submission.Payload payload = (Submission.Payload) o;

            return !(this.contentId != null ? !this.contentId.equals(payload.contentId) : payload.contentId != null);

        }

        @Override
        public int hashCode() {
            return this.contentId != null ? this.contentId.hashCode() : 0;
        }
    }
}
