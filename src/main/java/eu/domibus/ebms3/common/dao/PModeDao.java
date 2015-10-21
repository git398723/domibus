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

package eu.domibus.ebms3.common.dao;

import eu.domibus.common.configuration.model.*;
import eu.domibus.common.exception.EbMS3Exception;
import eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.AgreementRef;
import eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.PartyId;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * TODO: add class description
 */

public class PModeDao extends PModeProvider {

    private static final Log LOG = LogFactory.getLog(PModeDao.class);


    @Override
    public Party getSenderParty(final String pModeKey) {
        final TypedQuery<Party> query = this.entityManager.createNamedQuery("Party.findByName", Party.class);
        query.setParameter("NAME", this.getSenderPartyNameFromPModeKey(pModeKey));
        return query.getSingleResult();
    }

    @Override
    public Party getReceiverParty(final String pModeKey) {
        final TypedQuery<Party> query = this.entityManager.createNamedQuery("Party.findByName", Party.class);
        query.setParameter("NAME", this.getReceiverPartyNameFromPModeKey(pModeKey));
        return query.getSingleResult();
    }

    @Override
    public Service getService(final String pModeKey) {
        final TypedQuery<Service> query = this.entityManager.createNamedQuery("Service.findByName", Service.class);
        query.setParameter("NAME", this.getServiceNameFromPModeKey(pModeKey)); //FIXME enable multiple ServiceTypes with the same name
        return query.getSingleResult();
    }

    @Override
    public Action getAction(final String pModeKey) {
        final TypedQuery<Action> query = this.entityManager.createNamedQuery("Action.findByName", Action.class);
        query.setParameter("NAME", this.getActionNameFromPModeKey(pModeKey));
        return query.getSingleResult();
    }

    @Override
    public Agreement getAgreement(final String pModeKey) {
        final TypedQuery<Agreement> query = this.entityManager.createNamedQuery("Agreement.findByName", Agreement.class);
        query.setParameter("NAME", this.getAgreementRefNameFromPModeKey(pModeKey));
        return query.getSingleResult();
    }

    @Override
    public LegConfiguration getLegConfiguration(final String pModeKey) {
        final TypedQuery<LegConfiguration> query = this.entityManager.createNamedQuery("LegConfiguration.findByName", LegConfiguration.class);
        query.setParameter("NAME", this.getLegConfigurationNameFromPModeKey(pModeKey));
        return query.getSingleResult();
    }


    @Override //nothing to init here
    public void init() {

    }

    protected String findLegName(final String agreementRef, final String senderParty, final String receiverParty, final String service, final String action) throws EbMS3Exception {
        final Query candidatesQuery = this.entityManager.createNamedQuery("LegConfiguration.findForPartiesAndAgreements");
        candidatesQuery.setParameter("AGREEMENT", agreementRef);
        candidatesQuery.setParameter("SENDER_PARTY", senderParty);
        candidatesQuery.setParameter("RECEIVER_PARTY", receiverParty);
        final List<LegConfiguration> candidates = candidatesQuery.getResultList();
        if (candidates == null || candidates.isEmpty()) {
            throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0001, "No Candidates for Legs found", null, null, null);
        }
        final TypedQuery<String> query = this.entityManager.createNamedQuery("LegConfiguration.findForPMode", String.class);
        query.setParameter("SERVICE", service);
        query.setParameter("ACTION", action);
        final Collection<String> candidateIds = new ArrayList();
        for (final LegConfiguration candidate : candidates) {
            candidateIds.add(candidate.getName());
        }
        query.setParameter("CANDIDATES", candidateIds);
        try {
            return query.getSingleResult();
        } catch (final NoResultException e) {
            PModeDao.LOG.info("", e);
        }
        throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0001, "No matching leg found", null, null, null);
    }

    protected String findAgreementRef(final AgreementRef agreementRef) throws EbMS3Exception {
        if (agreementRef == null || agreementRef.getValue() == null || agreementRef.getValue().isEmpty()) {
            return ""; //AgreementRef is optional
        }
        final String value = agreementRef.getValue();
        final String pmode = agreementRef.getPmode(); //FIXME? This value is ignored!
        final String type = agreementRef.getType();
        final TypedQuery<String> query = this.entityManager.createNamedQuery("Agreement.findByValueAndType", String.class);
        query.setParameter("VALUE", value);
        query.setParameter("TYPE", (type == null) ? "" : type);
        try {
            return query.getSingleResult();
        } catch (final NoResultException e) {
            PModeDao.LOG.info("No matching agreementRef found", e);
        }
        throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0001, "No matching agreementRef found", null, null, null);//FIXME: Throw ValueInconsistent if CPA not recognized [5.2.2.7]
    }

    protected String findActionName(final String action) throws EbMS3Exception {
        if (action == null || action.isEmpty()) {
            throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0004, "Action parameter must not be null or empty", null, null, null);
        }

        final TypedQuery<String> query = this.entityManager.createNamedQuery("Action.findByAction", String.class);
        query.setParameter("ACTION", action);
        try {
            final String actionName = query.getSingleResult();
            return actionName;
        } catch (final NoResultException e) {
            PModeDao.LOG.info("No matching action found", e);
            throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0001, "No matching action found", null, null, null);
        }
    }

    protected String findServiceName(final eu.domibus.common.model.org.oasis_open.docs.ebxml_msg.ebms.v3_0.ns.core._200704.Service service) throws EbMS3Exception {
        final String type = service.getType();
        final String value = service.getValue();
        final TypedQuery<String> query;
        if (type == null || type.isEmpty()) {
            try {
                URI.create(value); //if not an URI an IllegalArgumentException will be thrown
                query = entityManager.createNamedQuery("Service.findWithoutType", String.class);
                query.setParameter("SERVICE", value);
            } catch (final IllegalArgumentException e) {
                final EbMS3Exception ex = new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0003, e, null);
                ex.setErrorDetail("Service " + value + " is not a valid URI [CORE] 5.2.2.8");
                throw ex;
            }
        } else {
            query = this.entityManager.createNamedQuery("Service.findByServiceAndType", String.class);
            query.setParameter("SERVICE", value);
            query.setParameter("TYPE", type);
        }
        try {
            return query.getSingleResult();
        } catch (final NoResultException e) {
            PModeDao.LOG.info("No machting service found", e);
            throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0001, "No machting service found", null, null, null);
        }
    }

    protected String findPartyName(final List<PartyId> partyIds) throws EbMS3Exception {
        Identifier identifier;
        for (final PartyId partyId : partyIds) {
            try {
                String type = partyId.getType();
                if (type == null || type.isEmpty()) { //PartyId must be an URI
                    try {
                        //noinspection ResultOfMethodCallIgnored
                        URI.create(partyId.getValue()); //if not an URI an IllegalArgumentException will be thrown
                        type = "";
                    } catch (final IllegalArgumentException e) {
                        final EbMS3Exception ex = new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0003, e, null);
                        ex.setErrorDetail("PartyId " + partyId.getValue() + " is not a valid URI [CORE] 5.2.2.3");
                        throw ex;
                    }
                }
                final TypedQuery<Identifier> identifierQuery = this.entityManager.createNamedQuery("Identifier.findByTypeAndPartyId", Identifier.class);
                identifierQuery.setParameter("PARTY_ID", partyId.getValue());
                identifierQuery.setParameter("PARTY_ID_TYPE", type);
                identifier = identifierQuery.getSingleResult();
                final TypedQuery<String> query = this.entityManager.createNamedQuery("Party.findPartyByIdentifier", String.class);
                query.setParameter("PARTY_IDENTIFIER", identifier);

                return query.getSingleResult();
            } catch (final NoResultException e) {
                PModeDao.LOG.debug("", e); // Its ok to not know all identifiers, we just have to know one
            }
        }
        throw new EbMS3Exception(EbMS3Exception.EbMS3ErrorCode.EBMS_0003, "No matching party found", null, null, null);
    }

    @Override
    public boolean isMpcExistant(final String mpc) {
        final TypedQuery<Integer> query = this.entityManager.createNamedQuery("Mpc.countForQualifiedName", Integer.class);
        return query.getSingleResult() > 0;
    }

    @Override
    public int getRetentionDownloadedByMpcName(final String mpcName) {
        final TypedQuery<Mpc> query = entityManager.createNamedQuery("Mpc.findByQualifiedName", Mpc.class);
        query.setParameter("QUALIFIED_NAME", mpcName);

        final Mpc result = query.getSingleResult();

        if (result == null) {
            PModeDao.LOG.error("No mpc with name: " + mpcName + " found. Assuming message retention of 0 for downloaded messages.");
            return 0;
        }

        return result.getRetentionDownloaded();
    }

    @Override
    public int getRetentionUndownloadedByMpcName(final String mpcName) {
        final TypedQuery<Mpc> query = this.entityManager.createNamedQuery("Mpc.findByQualifiedName", Mpc.class);
        query.setParameter("QUALIFIED_NAME", mpcName);
        final Mpc result = query.getSingleResult();

        if (result == null) {
            PModeDao.LOG.error("No mpc with name: " + mpcName + " found. Assuming message retention of -1 for undownloaded messages.");
            return 0;
        }

        return result.getRetentionUndownloaded();
    }

    @Override
    public List<String> getMpcList() {
        final TypedQuery<String> query = entityManager.createNamedQuery("Mpc.getAllNames", String.class);
        return query.getResultList();
    }
}