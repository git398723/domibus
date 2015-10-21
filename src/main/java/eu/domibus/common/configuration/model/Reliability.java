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
import eu.domibus.common.model.xmladapter.ReplyPatternAdapter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="replyPattern" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Entity
@Table(name = "TB_RELIABILITY")
public class Reliability extends AbstractBaseEntity {

    @XmlAttribute(name = "name", required = true)
    @Column(name = "NAME")
    protected String name;
    @XmlAttribute(name = "replyPattern", required = true)
    @XmlJavaTypeAdapter(ReplyPatternAdapter.class)
    @Column(name = "REPLY_PATTERN")
    @Enumerated(EnumType.STRING)
    protected ReplyPattern replyPattern;
    @XmlAttribute(name = "nonRepudiation", required = true)
    @Column(name = "NON_REPUDIATION")
    protected boolean nonRepudiation;

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
     * Gets the value of the replyPattern property.
     *
     * @return possible object is
     * {@link String }
     */
    public ReplyPattern getReplyPattern() {
        return this.replyPattern;
    }

    /**
     * Sets the value of the replyPattern property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReplyPattern(final ReplyPattern value) {
        this.replyPattern = value;
    }

    public boolean isNonRepudiation() {
        return this.nonRepudiation;
    }

    public void setNonRepudiation(final boolean nonRepudiation) {
        this.nonRepudiation = nonRepudiation;
    }

    public void init(final Configuration configuration) {

    }
}
