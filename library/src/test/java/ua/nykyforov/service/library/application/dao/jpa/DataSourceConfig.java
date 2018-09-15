package ua.nykyforov.service.library.application.dao.jpa;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import ua.nykyforov.service.library.core.domain.Book;

@Configuration
@EntityScan(basePackageClasses = Book.class)
@ComponentScan(basePackageClasses = JpaBookDao.class)
@AutoConfigurationPackage
@TestPropertySource("classpath:application.yml")
public class DataSourceConfig {
}
