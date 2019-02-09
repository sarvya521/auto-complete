package com.ac.repo.impl;

import com.ac.dto.MstCity;
import com.ac.repo.CityDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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
}
