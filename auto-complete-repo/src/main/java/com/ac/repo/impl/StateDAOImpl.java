package com.ac.repo.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.ac.dto.MstState;
import com.ac.repo.StateDAO;

/**
 * Implementation of {@link com.ac.repo.StateDAO}
 * 
 * @author sarvesh
 */
@Repository
public class StateDAOImpl implements StateDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MstState> getStates(String keyword) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MstState> cq = cb.createQuery(MstState.class);

        Root<MstState> state = cq.from(MstState.class);

        Predicate namePredicate = cb.like(cb.lower(state.get("name")), "%" + keyword.toLowerCase() + "%");
        cq.where(namePredicate);

        TypedQuery<MstState> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public List<MstState> getStates(String keyword, int maxResult) {
		List<MstState> result = new ArrayList<>();
		getStates(keyword.toLowerCase() + "%", maxResult, result);
        if(result.size() < maxResult) {
        	getStates("%" + keyword.toLowerCase() + "%", maxResult, result);
        }
        return result;
    }
    
    private void getStates(String keywordExpression, int maxResult, List<MstState> result) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MstState> cq = cb.createQuery(MstState.class);

        Root<MstState> state = cq.from(MstState.class);

        Predicate namePredicate = cb.like(cb.lower(state.get("name")), keywordExpression);
        cq.where(namePredicate);
        
        cq.orderBy(cb.asc(state.get("name")));
        
        TypedQuery<MstState> query = entityManager.createQuery(cq);
        query.setFirstResult(0).setMaxResults(maxResult - result.size());
        result.addAll(query.getResultList());
    }
    
}
