package cityapp.dao;

import cityapp.entity.City;

import jakarta.persistence.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CityJPADao implements CityDAO {
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public CityJPADao() {
        emf = Persistence.createEntityManagerFactory("cityapp-pu");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("CREATE TABLE IF NOT EXISTS cities (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR, country VARCHAR, population INT)").executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public void save(City city) {
        em.getTransaction().begin();
        em.persist(city);
        em.getTransaction().commit();
    }

    @Override
    public List<City> findAll() {
        return em.createQuery("SELECT c FROM City c", City.class).getResultList();
    }
}
