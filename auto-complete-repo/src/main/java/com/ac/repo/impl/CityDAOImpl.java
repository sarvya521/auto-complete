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

@Repository
public class CityDAOImpl implements CityDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MstCity> getCities(String keyword) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MstCity> cq = cb.createQuery(MstCity.class);

        Root<MstCity> city = cq.from(MstCity.class);

        Predicate namePredicate = cb.like(city.get("name"), "%" + keyword + "%");
        cq.where(namePredicate);

        cq.orderBy(cb.asc(city.get("name")));

        TypedQuery<MstCity> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
