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

package eu.domibus.common.dao;

import eu.domibus.common.model.AbstractBaseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

/**
 * A basic DAO implementation providing the standard CRUD operations,
 *
 * @author Christian Koch
 * @since 3.0
 */

@Transactional
public abstract class BasicDao<T extends AbstractBaseEntity> {
    protected static final Log LOG = LogFactory.getLog(BasicDao.class);
    private final Class<T> typeOfT;
    @PersistenceContext
    protected EntityManager em;

    /**
     * @param typeOfT The entity class this DAO provides access to
     */
    public BasicDao(final Class<T> typeOfT) {
        this.typeOfT = typeOfT;
    }

    @Transactional
    public void create(final T entity) {
        this.em.persist(entity);
    }

    @Transactional
    public void delete(final T entity) {
        this.em.remove(this.em.merge(entity));
    }

    public T read(final int id) {
        return this.em.find(this.typeOfT, id);
    }

    @Transactional
    public void updateAll(final Collection<T> update) {
        for (final T t : update) {
            this.update(t);
        }
    }

    @Transactional
    public void update(final T entity) {
        this.em.merge(entity);
    }
}
