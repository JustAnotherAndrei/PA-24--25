import entities.City;
import repositories.CityRepository;
import util.JpaUtil;
import util.LoggerUtil;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerUtil.createLogger("CityApp");
        EntityManager em = JpaUtil.getEntityManager();

        CityRepository repo = new CityRepository(em, logger);

        City city1 = new City("Iasi", 100, 100);
        City city2 = new City("Bacau", 150, 150);
        city1.getSisters().add(city2);
        city2.getSisters().add(city1);

        repo.save(city1);
        repo.save(city2);

        City fetched = repo.findByName("Iasi");
        logger.info("Fetched city: " + fetched.getName());
    }
}
