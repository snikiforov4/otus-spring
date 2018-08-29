package ua.nykyforov.service.library.application.repository;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import ua.nykyforov.service.library.core.domain.Book;

@Configuration
@EntityScan(basePackageClasses = Book.class)
@EnableJpaRepositories(basePackageClasses = AuthorRepository.class)
@AutoConfigurationPackage
@TestPropertySource("classpath:application-test.yml")
public class DataSourceConfig {
}
