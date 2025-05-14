package repositories;

import entities.SisterRelation;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

public class SisterRelationRepository extends AbstractRepository<SisterRelation, Integer> {

    public SisterRelationRepository(EntityManager em, Logger logger) {
        super(em, SisterRelation.class, logger);
    }
}
