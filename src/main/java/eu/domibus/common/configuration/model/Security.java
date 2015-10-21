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
 *       &lt;attribute name="policy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="signatureMethod" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Entity
@Table(name = "TB_SECURITY")
public class Security extends AbstractBaseEntity {

    @XmlAttribute(name = "name", required = true)
    @Column(name = "NAME")
    protected String name;
    @XmlAttribute(name = "policy", required = true)
    @Column(name = "POLICY")
    protected String policy;
    @XmlAttribute(name = "signatureMethod", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "SIGNATURE_METHOD")
    protected AsymmetricSignatureAlgorithm signatureMethod;

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
     * Gets the value of the policy property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPolicy() {
        return this.policy;
    }

    /**
     * Sets the value of the policy property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPolicy(final String value) {
        this.policy = value;
    }

    /**
     * Gets the value of the signatureMethod property.
     *
     * @return possible object is
     * {@link AsymmetricSignatureAlgorithm }
     */
    public AsymmetricSignatureAlgorithm getSignatureMethod() {
        return this.signatureMethod;
    }

    /**
     * Sets the value of the signatureMethod property.
     *
     * @param value allowed object is
     *              {@link AsymmetricSignatureAlgorithm }
     */
    public void setSignatureMethod(final AsymmetricSignatureAlgorithm value) {
        this.signatureMethod = value;
    }

    public void init(final Configuration configuration) {

    }
}
