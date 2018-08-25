package ua.nykyforov.twitter.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = TweetRepository.class)
public class MongoConfig {
}
