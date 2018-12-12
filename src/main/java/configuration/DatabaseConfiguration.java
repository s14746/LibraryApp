package configuration;

import org.hsqldb.jdbc.JDBCDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Profile("!mock-db")
    @Bean
    public DataSource dataSource() {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setURL("jdbc:hsqldb:hsql://localhost/workdb");
        dataSource.setUser("SA");
        dataSource.setPassword("");
        return dataSource;
    }

    @Profile("mock-db")
    @Bean
    public DataSource dataSourceMem() {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setURL("jdbc:hsqldb:mem:bookdb");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
