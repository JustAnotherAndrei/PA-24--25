package repositories;

import entities.City;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

public class CityRepository extends AbstractRepository<City, Integer> {

    public CityRepository(EntityManager em, Logger logger) {
        super(em, City.class, logger);
    }

    public City findByName(String name) {
        long start = System.currentTimeMillis();
        City result = em.createQuery("SELECT c FROM City c WHERE c.name = :name", City.class)
                .setParameter("name", name)
                .getSingleResult();
        logQuery("findByName", start);
        return result;
    }
}
