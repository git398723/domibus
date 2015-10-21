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
 *       &lt;attribute name="errorAsResponse" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="businessErrorNotifyProducer" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="businessErrorNotifyConsumer" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="deliveryFailureNotifyProducer" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Entity
@Table(name = "TB_ERROR_HANDLING")
public class ErrorHandling extends AbstractBaseEntity {

    @XmlAttribute(name = "name", required = true)
    @Column(name = "NAME")
    protected String name;
    @XmlAttribute(name = "errorAsResponse", required = true)
    @Column(name = "ERROR_AS_RESPONSE")
    protected boolean errorAsResponse;
    @XmlAttribute(name = "businessErrorNotifyProducer", required = true)
    @Column(name = "BUSINESS_ERROR_NOTIFY_PRODUCER")
    protected boolean businessErrorNotifyProducer;
    @XmlAttribute(name = "businessErrorNotifyConsumer", required = true)
    @Column(name = "BUSINESS_ERROR_NOTIFY_CONSUMER")
    protected boolean businessErrorNotifyConsumer;
    @XmlAttribute(name = "deliveryFailureNotifyProducer", required = true)
    @Column(name = "DELIVERY_FAIL_NOTIFY_PRODUCER")
    protected boolean deliveryFailureNotifyProducer;

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
     * Gets the value of the errorAsResponse property.
     */
    public boolean isErrorAsResponse() {
        return this.errorAsResponse;
    }

    /**
     * Sets the value of the errorAsResponse property.
     */
    public void setErrorAsResponse(final boolean value) {
        this.errorAsResponse = value;
    }

    /**
     * Gets the value of the businessErrorNotifyProducer property.
     */
    public boolean isBusinessErrorNotifyProducer() {
        return this.businessErrorNotifyProducer;
    }

    /**
     * Sets the value of the businessErrorNotifyProducer property.
     */
    public void setBusinessErrorNotifyProducer(final boolean value) {
        this.businessErrorNotifyProducer = value;
    }

    /**
     * Gets the value of the businessErrorNotifyConsumer property.
     */
    public boolean isBusinessErrorNotifyConsumer() {
        return this.businessErrorNotifyConsumer;
    }

    /**
     * Sets the value of the businessErrorNotifyConsumer property.
     */
    public void setBusinessErrorNotifyConsumer(final boolean value) {
        this.businessErrorNotifyConsumer = value;
    }

    /**
     * Gets the value of the deliveryFailureNotifyProducer property.
     */
    public boolean isDeliveryFailureNotifyProducer() {
        return this.deliveryFailureNotifyProducer;
    }

    /**
     * Sets the value of the deliveryFailureNotifyProducer property.
     */
    public void setDeliveryFailureNotifyProducer(final boolean value) {
        this.deliveryFailureNotifyProducer = value;
    }

    public void init(final Configuration configuration) {

    }
}
