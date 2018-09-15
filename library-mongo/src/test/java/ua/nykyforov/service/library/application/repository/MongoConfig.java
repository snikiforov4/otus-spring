package ua.nykyforov.service.library.application.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = BookRepository.class)
public class MongoConfig {
}
