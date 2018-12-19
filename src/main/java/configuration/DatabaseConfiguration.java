package configuration;

import org.hibernate.SessionFactory;
import org.hsqldb.jdbc.JDBCDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
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

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(
            DataSource dataSource
    ) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.setProperty("hibernate.show_sql", "true");

        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.setPackagesToScan("domain");
        return localSessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager(
            SessionFactory sessionFactory
    ) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }
}
