package com.ac.repo.impl;

import com.ac.dto.MstState;
import com.ac.repo.StateDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class StateDAOImpl implements StateDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MstState> getStates(String keyword) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MstState> cq = cb.createQuery(MstState.class);

        Root<MstState> state = cq.from(MstState.class);

        Predicate namePredicate = cb.like(state.get("name"), "%" + keyword + "%");
        cq.where(namePredicate);

        cq.orderBy(cb.asc(state.get("name")));

        TypedQuery<MstState> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
