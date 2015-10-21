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

package eu.domibus.submission.handler;

import eu.domibus.common.MessageStatus;
import eu.domibus.common.model.logging.ErrorLogEntry;
import eu.domibus.submission.validation.exception.ValidationException;

import java.util.Collection;
import java.util.List;

/**
 * Implementations of this interface handle the retrieval of messages from
 * Domibus to the backend.
 *
 * @param <T> Data transfer object
 *            (http://en.wikipedia.org/wiki/Data_transfer_object) transported between the
 *            backend and Domibus
 * @author Christian Koch
 * @author Stefan Müller
 * @since 3.0
 */
public interface MessageRetriever<T> {

    /**
     * provides the message with the corresponding messageId
     *
     * @param messageId the messageId of the message to retrieve
     * @return the message object with the given messageId
     * @throws eu.domibus.submission.validation.exception.ValidationException
     */
    T downloadMessage(String messageId) throws ValidationException;

    /**
     * provides a list of messageIds which have not been downloaded yet
     *
     * @return a list of messages that have not been downloaded yet
     */
    Collection<String> listPendingMessages();

    /**
     * Returns message status {@link eu.domibus.common.MessageStatus} for message with messageid
     *
     * @param messageId id of the message the status is requested for
     * @return the message status {@link eu.domibus.common.MessageStatus}
     */
    MessageStatus getMessageStatus(String messageId);

    /**
     * Returns List {@link java.util.List} of error logs {@link eu.domibus.common.model.logging.ErrorLogEntry} for message with messageid
     *
     * @param messageId id of the message the errors are requested for
     * @return the list of error log entries {@link java.util.List<eu.domibus.common.model.logging.ErrorLogEntry>}
     */
    List<ErrorLogEntry> getErrorsForMessage(String messageId);
}
