package configuration;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
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
public class DBUnitConfiguration {

    @Bean
    public HsqldbDataTypeFactory hsqldbDataTypeFactory() {
        return new HsqldbDataTypeFactory();
    }

    @Bean
    public DatabaseConfigBean databaseConfigBean(
            HsqldbDataTypeFactory hsqldbDataTypeFactory
    ) {
        DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
        databaseConfigBean.setSkipOracleRecyclebinTables(true);
        databaseConfigBean.setDatatypeFactory(hsqldbDataTypeFactory);
        return databaseConfigBean;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(
            DatabaseConfigBean databaseConfigBean,
            DataSource dataSource
    ) {
        DatabaseDataSourceConnectionFactoryBean databaseDataSourceConnectionFactoryBean = new DatabaseDataSourceConnectionFactoryBean();
        databaseDataSourceConnectionFactoryBean.setDatabaseConfig(databaseConfigBean);
        databaseDataSourceConnectionFactoryBean.setDataSource(dataSource);
        return databaseDataSourceConnectionFactoryBean;
    }
}
