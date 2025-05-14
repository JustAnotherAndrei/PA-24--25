package repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public abstract class AbstractRepository<T, ID> {
    protected EntityManager em;
    protected Class<T> entityClass;
    protected Logger logger;

    public AbstractRepository(EntityManager em, Class<T> entityClass, Logger logger) {
        this.em = em;
        this.entityClass = entityClass;
        this.logger = logger;
    }

    public void save(T entity) {
        executeInsideTransaction(em -> em.persist(entity));
    }

    public T findById(ID id) {
        long start = System.currentTimeMillis();
        T result = em.find(entityClass, id);
        logQuery("findById", start);
        return result;
    }

    public List<T> findAll() {
        long start = System.currentTimeMillis();
        List<T> result = em.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
        logQuery("findAll", start);
        return result;
    }

    public void delete(T entity) {
        executeInsideTransaction(em -> em.remove(entity));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            logger.severe("Transaction failed: " + e.getMessage());
            throw e;
        }
    }

    void logQuery(String method, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        String message = method + " took " + duration + " ms";
        logger.info(message);
    }
}
