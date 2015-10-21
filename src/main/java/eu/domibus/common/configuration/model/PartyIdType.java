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
 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Entity
@Table(name = "TB_PARTY_ID_TYPE")
@NamedQueries(@NamedQuery(name = "PartyIdType.findByValue", query = "select p from PartyIdType p where p.value = :VALUE"))
public class PartyIdType extends AbstractBaseEntity {

    @XmlAttribute(name = "name", required = true)
    @Column(name = "NAME")
    protected String name;
    @XmlAttribute(name = "value", required = true)
    @XmlSchemaType(name = "anyURI")
    @Column(name = "VALUE")
    protected String value;

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
     * Gets the value of the value property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValue(final String value) {
        this.value = value;
    }

    void init(final Configuration configuration) {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PartyIdType)) return false;
        if (!super.equals(o)) return false;

        final PartyIdType that = (PartyIdType) o;

        if (!this.name.equals(that.name)) return false;
        return this.value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.value.hashCode();
        return result;
    }
}
