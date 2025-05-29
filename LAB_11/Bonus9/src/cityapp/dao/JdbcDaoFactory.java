package cityapp.dao;

public class JdbcDaoFactory implements DaoFactory {
    @Override
    public CityJPADao createCityDAO() {
        return new CityJDBCDao();
    }
}
