package cityapp.dao;

public class JpaDaoFactory implements DaoFactory {
    @Override
    public CityJPADao createCityDAO() {
        return new CityJPADao();
    }
}
