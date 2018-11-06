package ua.nykyforov.migratetool.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import ua.nykyforov.service.library.core.domain.Author;

import static ua.nykyforov.migratetool.JobNames.RDB_TO_NO_SQL;

@Configuration
@EnableBatchProcessing
public class AppConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public AppConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job rdbToNoSqlJob(Step processAuthors) {
        return this.jobBuilderFactory.get(RDB_TO_NO_SQL)
                .flow(processAuthors)
                .end()
                .build();
    }

    @Bean
    public Step processAuthors(ItemReader<Author> authorReader,
                               ItemWriter<Author> authorWriter) {
        return this.stepBuilderFactory.get("step1")
                .<Author, Author>chunk(10)
                .reader(authorReader)
                .writer(authorWriter)
                .build();
    }

    @Bean
    public ItemReader<Author> authorReader(LocalSessionFactoryBean sessionFactory) {
        return new HibernateCursorItemReaderBuilder<Author>()
                .name("authorReader")
                .sessionFactory(sessionFactory.getObject())
                .queryString("from Author")
                .build();
    }

    @Bean
    public ItemWriter<Author> authorWriter() {
        return System.out::println;
    }

}
