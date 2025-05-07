package com.example.app.repository;

import com.example.app.entity.Person;
import com.example.app.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PersonRepository {

    public void create(Person person) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Person findById(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    public List<Person> findByName(String namePattern) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Person> query = em.createNamedQuery("Person.findByName", Person.class);
            query.setParameter("name", namePattern);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
