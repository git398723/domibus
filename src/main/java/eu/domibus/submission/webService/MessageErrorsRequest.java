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

package eu.domibus.submission.webService;

import javax.xml.bind.annotation.*;

/**
 * TODO: add class description
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "messageID")
@XmlRootElement(name = "messageErrorsRequest")
public class MessageErrorsRequest {

    @XmlElement(required = true)
    protected String messageID;

    public String getMessageID() {
        return this.messageID;
    }

    public void setMessageID(final String messageID) {
        this.messageID = messageID;
    }
}
