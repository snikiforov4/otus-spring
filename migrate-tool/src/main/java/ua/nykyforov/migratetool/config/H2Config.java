package ua.nykyforov.migratetool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static java.util.Objects.requireNonNull;

@Configuration
@PropertySource("h2.properties")
public class H2Config {

    private Environment env;

    @Autowired
    public H2Config(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource h2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(requireNonNull(env.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(requireNonNull(env.getProperty("jdbc.url")));
        dataSource.setUsername(requireNonNull(env.getProperty("jdbc.username")));
        dataSource.setPassword(requireNonNull(env.getProperty("jdbc.password")));
        return dataSource;
    }

}
