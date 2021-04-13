import org.apache.commons.dbcp2.BasicDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class HqlRun {
    private final Properties properties = new Properties();
    private final BasicDataSource pool = new BasicDataSource();

    public HqlRun() throws IOException, SQLException {
        this.settingUpPool();
    }

    private void settingUpPool() throws IOException, SQLException {
            properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));
            pool.setDriverClassName(properties.getProperty("db.driver"));
            pool.setUrl(properties.getProperty("db.url"));
            pool.setUsername(properties.getProperty("db.user"));
            pool.setPassword(properties.getProperty("db.password"));
            StringBuilder builder = new StringBuilder();
             List<String> strings = Files.readAllLines(Path.of(("generate_schema.sql")));
             strings.forEach(line -> builder.append(line).append(System.lineSeparator()));
            getConnection().prepareStatement(builder.toString()).executeUpdate();
            StringBuilder builder2 = new StringBuilder();
            List<String> strings2 = Files.readAllLines(Path.of(("insertValues.sql")));
            strings2.forEach(line -> builder2.append(line).append(System.lineSeparator()));
            Connection connection2 =pool.getConnection();
            connection2.prepareStatement(builder2.toString()).executeUpdate();

    }

    public Connection getConnection()  {
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (SQLException throwables) {
            System.out.println(throwables.getCause());
            System.out.println(throwables.getMessage());
        }
        return con;
    }
    public ResultSet getGroupingBy() throws SQLException {
       return getConnection().prepareStatement("SELECT DEPARTMENT, SUM(SALARY)  FROM EMPLOYEES GROUP BY DEPARTMENT;").executeQuery();
    }
}
