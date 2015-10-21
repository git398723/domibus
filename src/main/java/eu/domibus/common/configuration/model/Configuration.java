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

package eu.domibus.common.configuration.model;

import eu.domibus.common.model.AbstractBaseEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@Entity
@Table(name = "TB_CONFIGURATION")
@XmlRootElement(name = "configuration")
@NamedQueries({@NamedQuery(name = "Configuration.count", query = "SELECT COUNT(c.entityId) FROM Configuration c"), @NamedQuery(name = "Configuration.getConfiguration", query = "select conf from Configuration conf")})
public class Configuration extends AbstractBaseEntity {

    @XmlElement(required = true, name = "businessProcesses")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_BUSINESSPROCESSES")
    protected BusinessProcesses businessProcesses;
    @XmlElement(required = true, name = "mpcs")
    @Transient
    private Mpcs mpcsXml;
    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CONFIGURATION")
    private List<Mpc> mpcs;
    @XmlAttribute(name = "party", required = true)
    @Transient
    private String partyXml;
    @XmlTransient
    @JoinColumn(name = "FK_PARTY")
    @OneToOne
    private Party party;

    private void initMpcs() {
        if (this.mpcs == null) {
            this.mpcs = this.mpcsXml.getMpc();
        }
    }

    private void initParty() {
        for (final Party party1 : this.businessProcesses.getParties()) {
            if (party1.getName().equals(this.partyXml)) {
                this.party = party1;
                break;
            }
        }
    }

    public BusinessProcesses getBusinessProcesses() {
        return this.businessProcesses;
    }

    public void setBusinessProcesses(final BusinessProcesses businessProcesses) {
        this.businessProcesses = businessProcesses;
    }

    public Party getParty() {
        return this.party;
    }

    public void setParty(final Party party) {
        this.party = party;
    }

    public List<Mpc> getMpcs() {
        return this.mpcs;
    }

    public void setMpcs(final List<Mpc> mpcs) {
        this.mpcs = mpcs;
    }

    @PrePersist
    private void preparePersist() {
        this.initMpcs();
        this.businessProcesses.init(this);

        this.initParty();
    }


}
