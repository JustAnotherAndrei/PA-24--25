package db;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private static final HikariDataSource ds;
    static {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:mysql://localhost:3306/worldcities?useSSL=false&serverTimezone=UTC");
        cfg.setUsername("your_user");
        cfg.setPassword("your_pass");
        cfg.setMaximumPoolSize(10);
        ds = new HikariDataSource(cfg);
    }
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    public static void closePool() {
        ds.close();
    }
}

