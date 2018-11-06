package ua.nykyforov.migratetool.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CustomBatchConfigurer extends DefaultBatchConfigurer {

    public CustomBatchConfigurer(@Qualifier("h2DataSource") DataSource dataSource) {
        super(dataSource);
    }

}
