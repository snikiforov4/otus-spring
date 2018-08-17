package ua.nykyforov.service.library.application.dao.jdbc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ComponentScan(basePackageClasses = JdbcBookDao.class)
@TestPropertySource("classpath:application-test.yml")
public class DataSourceConfig {
}
