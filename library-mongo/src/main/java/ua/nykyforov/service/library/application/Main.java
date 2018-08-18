package ua.nykyforov.service.library.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ua.nykyforov.service.library.application.repository.BookRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = BookRepository.class)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}