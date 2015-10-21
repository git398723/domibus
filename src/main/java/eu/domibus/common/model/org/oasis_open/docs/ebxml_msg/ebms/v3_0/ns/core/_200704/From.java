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

package eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The REQUIRED element occurs once, and contains information describing the originating party.
 *
 * @author Christian Koch
 * @version 1.0
 * @since 3.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "From", propOrder = {"partyId", "role"})
@Embeddable
public class From {

    public static final String DEFAULT_ROLE = "http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultRole";

    @XmlElement(name = "PartyId", required = true)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FROM_ID")
    protected List<PartyId> partyId;
    @XmlElement(name = "Role", required = true, defaultValue = From.DEFAULT_ROLE)
    @Column(name = "FROM_ROLE")
    protected String role;

    /**
     * The REQUIRED PartyId element occurs one or more times. If it occurs multiple times, each instance
     * MUST identify the same organization.
     * <p/>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the partyId property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartyId().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link PartyId }
     */
    public List<PartyId> getPartyId() {
        if (this.partyId == null) {
            this.partyId = new ArrayList<>();
        }
        return this.partyId;
    }

    /**
     * The REQUIRED
     * eb:Role element occurs once, and identifies the authorized role (fromAuthorizedRole or
     * toAuthorizedRole) of the Party sending (when present as a child of the From element) or receiving
     * (when present as a child of the To element) the message. The value of the Role element is a nonempty
     * string, with a default value of
     * http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultRole .
     * Other possible values are subject to partner agreement.
     *
     * @return possible object is {@link String }
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Sets the value of the role property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRole(final String value) {
        this.role = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof From)) return false;

        final From from = (From) o;
        Collections.sort(this.partyId);
        Collections.sort(from.getPartyId());
        if (!this.partyId.equals(from.partyId)) return false;
        return !(this.role != null ? !this.role.equals(from.role) : from.role != null);

    }

    @Override
    public int hashCode() {
        int result = this.partyId.hashCode();
        result = 31 * result + (this.role != null ? this.role.hashCode() : 0);
        return result;
    }
}
