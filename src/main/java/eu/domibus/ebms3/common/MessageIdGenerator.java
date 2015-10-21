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

package eu.domibus.ebms3.common;

import java.util.UUID;

/**
 * TODO: add class description
 */
public class MessageIdGenerator {

    private String messageIdSuffix;

    public String generateMessageId() {
        return UUID.randomUUID() + "@" + this.messageIdSuffix;
    }

    public void setMessageIdSuffix(String messageIdSuffix) {
        this.messageIdSuffix = messageIdSuffix;
    }
}
