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

package eu.domibus.common.model.org.w3._2005._05.xmlmime;

import eu.domibus.submission.webService.generated.PayloadType;

import javax.xml.bind.annotation.*;

/**
 * <p/>
 * Java class for base64Binary complex type.
 * <p/>
 * <p/>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p/>
 * <pre>
 * &lt;complexType name="base64Binary">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
 *       &lt;attribute ref="{http://www.w3.org/2005/05/xmlmime}contentType"/>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "base64Binary", propOrder = {"value"})
@XmlSeeAlso({PayloadType.class})
public class Base64Binary {

    @XmlValue
    protected byte[] value;
    @XmlAttribute(name = "contentType", namespace = "http://www.w3.org/2005/05/xmlmime")
    protected String contentType;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is byte[]
     */
    public byte[] getValue() {
        return this.value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is byte[]
     */
    public void setValue(final byte[] value) {
        this.value = value;
    }

    /**
     * Gets the value of the contentType property.
     *
     * @return possible object is {@link String }
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * Sets the value of the contentType property.
     *
     * @param value allowed object is {@link String }
     */
    public void setContentType(final String value) {
        this.contentType = value;
    }
}
