package ua.nykyforov.service.library.application.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.TestPropertySource;

@Configuration
@EnableMongoRepositories(basePackageClasses = BookRepository.class)
@TestPropertySource("classpath:application-test.yml")
public class MongoConfig {
}
