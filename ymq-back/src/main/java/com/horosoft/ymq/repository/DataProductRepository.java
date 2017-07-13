package com.horosoft.ymq.repository;

/**
 * Created by eugen on 7/11/2017.
 */

import com.horosoft.ymq.model.DataProduct;
import com.horosoft.ymq.utils.NumberGenerator;
import com.horosoft.ymq.utils.TextUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;
@Transactional(SUPPORTS)
public class DataProductRepository {


    // ======================================
    // =          Injection Points          =
    // ======================================

    @PersistenceContext(unitName = "ymqStorePU")
    private EntityManager em;

    @Inject
    private NumberGenerator generator;

    @Inject
    private TextUtil textUtil;

    // ======================================
    // =          Business methods          =
    // ======================================

    public DataProduct find(@NotNull Long id) {
        return em.find(DataProduct.class, id);
    }

    public List<DataProduct> findAll() {
        TypedQuery<DataProduct> query = em.createQuery("SELECT b FROM DataProduct b ORDER BY b.title DESC", DataProduct.class);
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM DataProduct b", Long.class);
        return query.getSingleResult();
    }

    @Transactional(REQUIRED)
    public DataProduct create(@NotNull DataProduct dataProduct) {
        dataProduct.setIsbn(generator.generateNumber());
        dataProduct.setTitle(textUtil.sanitize(dataProduct.getTitle()));
        em.persist(dataProduct);
        return dataProduct;
    }

    @Transactional(REQUIRED)
    public void delete(@NotNull Long id) {
        em.remove(em.getReference(DataProduct.class, id));
    }
}
