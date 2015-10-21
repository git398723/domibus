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

package eu.domibus.ebms3.common;

import eu.domibus.common.exception.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.BusFactory;
import org.apache.cxf.ws.policy.PolicyBuilder;
import org.apache.neethi.Policy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * TODO: add class description
 */
@Service
public class PolicyFactory {

    private static final Log LOG = LogFactory.getLog(PolicyFactory.class);

    @Cacheable("policyCache")
    public Policy parsePolicy(String location) throws ConfigurationException {
        PolicyBuilder pb = BusFactory.getDefaultBus().getExtension(PolicyBuilder.class);
        try {
            return pb.getPolicy(new FileInputStream(new File(System.getProperty("domibus.config.location"), location)));
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new ConfigurationException(e);
        }
    }


}
