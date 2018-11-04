package ua.nykyforov.migratetool.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ua.nykyforov.migratetool.JobNames.RDB_TO_NO_SQL;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job rdbToNoSqlJob() {
        return this.jobBuilderFactory.get(RDB_TO_NO_SQL)
                .start(loadRdbEntry())
                // .end()
                .build();
    }

    private Step loadRdbEntry() { // todo
        return null;
    }

}
