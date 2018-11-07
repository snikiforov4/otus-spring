package ua.nykyforov.migratetool.config;

import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ua.nykyforov.migratetool.converter.AuthorConverter;
import ua.nykyforov.service.library.core.domain.Author;

import static ua.nykyforov.migratetool.JobNames.RDB_TO_NO_SQL;

@Configuration
@EnableBatchProcessing
public class AppConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final AuthorConverter authorConverter;

    @Autowired
    public AppConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                     AuthorConverter authorConverter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.authorConverter = authorConverter;
    }

    @Bean
    public JobOperator jobOperator(JobExplorer jobExplorer, JobLauncher jobLauncher,
                                   JobRegistry jobRegistry, JobRepository jobRepository) {
        SimpleJobOperator jobOperator = new SimpleJobOperator();
        jobOperator.setJobExplorer(jobExplorer);
        jobOperator.setJobLauncher(jobLauncher);
        jobOperator.setJobRegistry(jobRegistry);
        jobOperator.setJobRepository(jobRepository);
        return jobOperator;
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Bean(RDB_TO_NO_SQL)
    public Job rdbToNoSqlJob(Step processAuthors) {
        return this.jobBuilderFactory.get(RDB_TO_NO_SQL)
                .flow(processAuthors)
                .end()
                .build();
    }

    @Bean
    public Step processAuthors(ItemReader<Author> authorReader,
                               ItemWriter<ua.nykyforov.service.library.application.domain.Author> authorWriter) {
        return this.stepBuilderFactory.get("step1")
                .<Author, ua.nykyforov.service.library.application.domain.Author>chunk(10)
                .reader(authorReader)
                .processor((ItemProcessor<Author, ua.nykyforov.service.library.application.domain.Author>)
                        authorConverter::convert)
                .writer(authorWriter)
                .build();
    }

    @Bean
    public ItemReader<Author> authorReader(SessionFactory sessionFactory) {
        return new HibernateCursorItemReaderBuilder<Author>()
                .name("authorReader")
                .sessionFactory(sessionFactory)
                .queryString("from Author")
                .useStatelessSession(true)
                .build();
    }

    @Bean
    public ItemWriter<ua.nykyforov.service.library.application.domain.Author> authorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<ua.nykyforov.service.library.application.domain.Author>()
                .collection("authors")
                .template(mongoTemplate)
                .build();
    }

}
