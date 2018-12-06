package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.BookService;
import service.BookServiceDatabaseImpl;
import service.DateService;

import javax.sql.DataSource;

@Configuration
public class BookConfiguration {
    @Bean
    public BookService bookService(
            DataSource dataSource,
            DateService dateService
    ) {
        return new BookServiceDatabaseImpl(dataSource, dateService);
    }
}
