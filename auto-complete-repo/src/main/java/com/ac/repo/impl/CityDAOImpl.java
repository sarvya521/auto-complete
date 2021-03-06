package com.ac.repo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.ac.dto.MstCity;
import com.ac.repo.CityDAO;

/**
 * Implementation of {@link com.ac.repo.StateDAO}
 * 
 * @author sarvesh
 */
@Repository
public class CityDAOImpl implements CityDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MstCity> getCities(String keyword) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MstCity> cq = cb.createQuery(MstCity.class);

        Root<MstCity> city = cq.from(MstCity.class);

        Predicate namePredicate = cb.like(cb.lower(city.get("name")), "%" + keyword.toLowerCase() + "%");
        cq.where(namePredicate);
        
        TypedQuery<MstCity> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<MstCity> getCities(String keyword, int maxResult) {
    	List<MstCity> result = new ArrayList<>();
        getCitiesStartsWithKeyword(keyword, maxResult, result);
        if(result.size() < maxResult) {
            getCitiesContainsKeyword(keyword, maxResult, result);
        }
        return result;
    }

    private void getCitiesStartsWithKeyword(String keyword, int maxResult, List<MstCity> result) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MstCity> cq = cb.createQuery(MstCity.class);

        Root<MstCity> city = cq.from(MstCity.class);

        Predicate namePredicate = cb.like(cb.lower(city.get("name")), keyword.toLowerCase() + "%");
        cq.where(namePredicate);

        cq.orderBy(cb.asc(city.get("name")));

        TypedQuery<MstCity> query = entityManager.createQuery(cq);
        query.setFirstResult(0).setMaxResults(maxResult - result.size());
        result.addAll(query.getResultList());
    }

    private void getCitiesContainsKeyword(String keyword, int maxResult, List<MstCity> result) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MstCity> cq = cb.createQuery(MstCity.class);

        Root<MstCity> city = cq.from(MstCity.class);

        List<String> cityNames = result.stream().map(c -> c.getName()).collect(Collectors.toList());

        Predicate namePredicate = cb.and(cb.not(city.get("name").in(cityNames)),
                cb.like(cb.lower(city.get("name")), "%" + keyword.toLowerCase() + "%"));
        cq.where(namePredicate);

        cq.orderBy(cb.asc(city.get("name")));

        TypedQuery<MstCity> query = entityManager.createQuery(cq);
        query.setFirstResult(0).setMaxResults(maxResult - result.size());
        result.addAll(query.getResultList());
    }
}
