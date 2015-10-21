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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.domibus.common.util.xmladapter;

import eu.domibus.common.exception.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author muell16
 */
public class ToStringAdapter extends XmlAdapter<Node, List<String>> {

    private static final Log LOG = LogFactory.getLog(ToStringAdapter.class);

    private final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    @Override
    public List<String> unmarshal(Node v) throws Exception {
        if (v != null) {
            List<String> result = new ArrayList<>(1);
            result.add(this.nodeToString(v));

            return result;
        }

        return null;
    }

    @Override
    public Node marshal(List<String> v) throws Exception {
        if (v.size() > 1) {
            ToStringAdapter.LOG.warn("More than one Element in List. Processing skipped");
            return null;
        }

        return this.stringToNode(v.get(0));
    }

    private String nodeToString(Node node) {
        StringWriter sw = new StringWriter();

        try {
            Transformer t = this.transformerFactory.newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException e) {
            throw new ConfigurationException(e);
        }

        return sw.toString();
    }

    private Node stringToNode(String content) {
        try {
            Document doc = this.documentBuilderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(content)));

            if (doc.getChildNodes().getLength() == 1) {
                return doc.getChildNodes().item(1);
            }

        } catch (SAXException | IOException | ParserConfigurationException e) {
            ToStringAdapter.LOG.warn("Error during transformation of String to Node", e);
            return null;
        }

        return null;
    }

}