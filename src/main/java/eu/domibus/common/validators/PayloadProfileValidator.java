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

import eu.domibus.common.configuration.model.Payload;
import eu.domibus.common.configuration.model.PayloadProfile;
import eu.domibus.common.exception.EbMS3Exception;
import eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.Messaging;
import eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.PartInfo;
import eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.Property;
import eu.domibus.ebms3.common.dao.PModeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kochc01 on 09.06.2015.
 */
@Service
public class PayloadProfileValidator {

    @Autowired
    private PModeProvider pModeProvider;

    public void validate(final Messaging messaging, final String pmodeKey) throws EbMS3Exception {
        final List<Payload> modifiableProfileList = new ArrayList<>();
        final PayloadProfile profile = this.pModeProvider.getLegConfiguration(pmodeKey).getPayloadProfile();
        if (profile == null) {
            // no profile means everything is valid
            return;
        }
        modifiableProfileList.addAll(profile.getPayloads());
        int size = 0;
        for (final PartInfo partInfo : messaging.getUserMessage().getPayloadInfo().getPartInfo()) {
            Payload profiled = null;
            final String cid = (partInfo.getHref() == null ? "" : partInfo.getHref());
            for (final Payload p : modifiableProfileList) {
                if (p.getCid().equals(cid)) {
                    profiled = p;
                    break;
                }
            }
            if (profiled == null) {
                throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0010, "Payload profiling for this exchange does not include a payload with CID: " + cid, messaging.getUserMessage().getMessageInfo().getMessageId(), null, null);
            }
            modifiableProfileList.remove(profiled);
            final List<eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.Property> partProperties = partInfo.getPartProperties().getProperties();
            String mime = null;
            for (final Property partProperty : partProperties) {
                if (Property.MIME_TYPE.equals(partProperty.getName())) {
                    mime = partProperty.getValue();
                    break;
                }
            }
            if (mime == null) {
                throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0010, "Payload profiling for this exchange requires all message parts to declare a MimeType property" + partInfo.getHref(), messaging.getUserMessage().getMessageInfo().getMessageId(), null, null);
            }
            if ((!profiled.getMimeType().equals(mime)) ||
                    (partInfo.isInBody() != profiled.isInBody()) ||
                    (profiled.getMaxSize() > 0 && profiled.getMaxSize() < partInfo.getBinaryData().length)) {
                throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0010, "Payload profiling error: expected: " + profiled + ", got " + partInfo, messaging.getUserMessage().getMessageInfo().getMessageId(), null, null);
            }
            size += partInfo.getBinaryData().length;
            if (profile.getMaxSize() > 0 && size > profile.getMaxSize()) {
                throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0010, "Payload profiling error, max allowed size of combined elements is " + profile.getMaxSize(), null, null);
            }

        }
        for (final Payload payload : modifiableProfileList) {
            if (payload.isRequired()) {
                throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0010, "Payload profiling error, missing payload:" + payload, null, null);

            }
        }
    }
}
