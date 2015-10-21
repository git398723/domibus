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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;
import java.math.BigInteger;


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
 *       &lt;attribute name="cid" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="schemaFile" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="maxSize" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="required" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Entity
@Table(name = "TB_PAYLOAD")
public class Payload extends AbstractBaseEntity {

    @XmlAttribute(name = "name", required = true)
    @Column(name = "NAME")
    protected String name;
    @XmlAttribute(name = "cid", required = true)
    @Column(name = "CID")
    protected String cid;
    @XmlAttribute(name = "mimeType")
    @Column(name = "MIME_TYPE")
    protected String mimeType;
    @XmlAttribute(name = "schemaFile")
    @XmlSchemaType(name = "anyURI")
    @Column(name = "SCHEMA_FILE")
    protected String schemaFile;
    @XmlAttribute(name = "maxSize")
    @Column(name = "MAX_SIZE")
    protected int maxSize;
    @XmlAttribute(name = "required", required = true)
    @Column(name = "REQUIRED_")
    protected boolean required;
    @XmlAttribute(name = "inBody", required = true)
    @Column(name = "IN_BODY")
    protected boolean inBody;

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
     * Gets the value of the cid property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCid() {
        return this.cid;
    }

    /**
     * Sets the value of the cid property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCid(final String value) {
        this.cid = value;
    }

    /**
     * Gets the value of the mimeType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMimeType() {
        return this.mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMimeType(final String value) {
        this.mimeType = value;
    }

    /**
     * Gets the value of the schemaFile property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSchemaFile() {
        return this.schemaFile;
    }

    /**
     * Sets the value of the schemaFile property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSchemaFile(final String value) {
        this.schemaFile = value;
    }

    /**
     * Gets the value of the maxSize property.
     *
     * @return possible object is
     * {@link BigInteger }
     */
    public int getMaxSize() {
        return this.maxSize;
    }

    /**
     * Sets the value of the maxSize property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setMaxSize(final int value) {
        this.maxSize = value;
    }

    /**
     * Gets the value of the required property.
     */
    public boolean isRequired() {
        return this.required;
    }

    /**
     * Sets the value of the required property.
     */
    public void setRequired(final boolean value) {
        this.required = value;
    }

    public void init(final Configuration configuration) {

    }

    @Override
    public String toString() {
        return "Payload{" +
                "name='" + this.name + '\'' +
                ", cid='" + this.cid + '\'' +
                ", mimeType='" + this.mimeType + '\'' +
                ", schemaFile='" + this.schemaFile + '\'' +
                ", maxSize=" + this.maxSize +
                ", required=" + this.required +
                '}';
    }

    public boolean isInBody() {
        return this.inBody;
    }

}
