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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Entity
@Table(name = "TB_MPC")
@NamedQueries({@NamedQuery(name = "Mpc.countForQualifiedName", query = "select count(mpc) from Mpc mpc where mpc.qualifiedName=:QUALIFIED_NAME"),
        @NamedQuery(name = "Mpc.findByQualifiedName", query = "select mpc from Mpc mpc where mpc.qualifiedName=:QUALIFIED_NAME"),
        @NamedQuery(name = "Mpc.getAllNames", query = "select mpc.qualifiedName from Mpc mpc")})


public class Mpc extends AbstractBaseEntity {

    public static final String DEFAULT_MPC = "http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC";

    @XmlAttribute(name = "name", required = true)
    @Column(name = "name")
    protected String name;
    @XmlAttribute(name = "retention_downloaded", required = true)
    @Column(name = "RETENTION_DOWNLOADED")
    protected int retentionDownloaded;

    @XmlAttribute(name = "retention_undownloaded", required = true)
    @Column(name = "RETENTION_UNDOWNLOADED")
    protected int retentionUndownloaded;

    @XmlAttribute(name = "default", required = true)
    @Column(name = "DEFAULT_MPC")
    protected boolean defaultMpc;
    @XmlAttribute(name = "enabled", required = true)
    @Column(name = "IS_ENABLED")
    protected boolean enabled;
    @XmlAttribute(name = "qualifiedName", required = true)
    @Column(name = "QUALIFIED_NAME")
    protected String qualifiedName;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(final String value) {
        this.name = value;
    }

    /**
     * Gets the value of the retentionDownloaded property.
     *
     * @return possible object is
     * {@link BigInteger }
     */
    public int getRetentionDownloaded() {
        return this.retentionDownloaded;
    }

    /**
     * Sets the value of the retentionDownloaded property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setRetentionDownloaded(final int value) {
        this.retentionDownloaded = value;
    }

    /**
     * Gets the value of the retentionUndownloaded property.
     *
     * @return possible object is
     * {@link BigInteger }
     */
    public int getRetentionUndownloaded() {
        return this.retentionUndownloaded;
    }

    /**
     * Sets the value of the retentionUndownloaded property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setRetentionUndownloaded(final int value) {
        this.retentionUndownloaded = value;
    }

    /**
     * Gets the value of the default property.
     */
    public boolean isDefault() {
        return this.defaultMpc;
    }

    /**
     * Sets the value of the default property.
     */
    public void setDefault(final boolean value) {
        this.defaultMpc = value;
    }

    /**
     * Gets the value of the enabled property.
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Sets the value of the enabled property.
     */
    public void setEnabled(final boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the qualifiedName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getQualifiedName() {
        return this.qualifiedName;
    }

    /**
     * Sets the value of the qualifiedName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setQualifiedName(final String value) {
        this.qualifiedName = value;
    }

}
