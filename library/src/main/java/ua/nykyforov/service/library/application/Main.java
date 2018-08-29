package ua.nykyforov.service.library.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import ua.nykyforov.service.library.core.domain.Book;

@SpringBootApplication
@EntityScan(basePackageClasses = Book.class)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
