package configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.BookService;
import service.BookServiceHibernateImpl;
import service.DateService;

@Configuration
public class BookConfiguration {
    @Bean
    public BookService bookService(
            SessionFactory sessionFactory,
            DateService dateService
    ) {
        return new BookServiceHibernateImpl(sessionFactory, dateService);
    }
}
