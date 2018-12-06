package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import service.DateService;
import service.DateServiceImpl;
import service.DateServiceMock;

@Configuration
public class DateConfiguration {
    @Profile("!mock-date")
    @Bean
    public DateService dateService() {
        return new DateServiceImpl();
    }

    @Profile("mock-date")
    @Bean
    public DateService dateServiceMock() {
        return new DateServiceMock();
    }
}
