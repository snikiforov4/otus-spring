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
import ua.nykyforov.migratetool.converter.BookConverter;
import ua.nykyforov.migratetool.converter.GenreConverter;
import ua.nykyforov.migratetool.domain.PostgresAuthor;
import ua.nykyforov.migratetool.domain.PostgresBook;
import ua.nykyforov.migratetool.domain.PostgresGenre;
import ua.nykyforov.service.library.application.domain.Author;
import ua.nykyforov.service.library.application.domain.Book;
import ua.nykyforov.service.library.application.domain.Genre;

import static ua.nykyforov.migratetool.JobNames.RDB_TO_NO_SQL;

@Configuration
@EnableBatchProcessing
public class AppConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final AuthorConverter authorConverter;
    private final GenreConverter genreConverter;
    private final BookConverter bookConverter;

    @Autowired
    public AppConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                     AuthorConverter authorConverter, GenreConverter genreConverter, BookConverter bookConverter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.authorConverter = authorConverter;
        this.genreConverter = genreConverter;
        this.bookConverter = bookConverter;
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
    public Job rdbToNoSqlJob(Step processBooks, Step processAuthors, Step processGenres) {
        return this.jobBuilderFactory.get(RDB_TO_NO_SQL)
                .start(processBooks)
                .next(processAuthors)
                .next(processGenres)
                .build();
    }

    @Bean
    public Step processBooks(ItemReader<PostgresBook> bookReader,
                             ItemWriter<Book> bookWriter) {
        return this.stepBuilderFactory.get("books")
                .<PostgresBook, Book>chunk(10)
                .reader(bookReader)
                .processor((ItemProcessor<PostgresBook, Book>) bookConverter::convert)
                .writer(bookWriter)
                .build();
    }

    @Bean
    public ItemReader<PostgresBook> bookReader(SessionFactory sessionFactory) {
        return new HibernateCursorItemReaderBuilder<PostgresBook>()
                .name("bookReader")
                .sessionFactory(sessionFactory)
                .queryString("from Book")
                .build();
    }

    @Bean
    public ItemWriter<Book> bookWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Book>()
                .collection("books")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step processAuthors(ItemReader<PostgresAuthor> authorReader,
                               ItemWriter<Author> authorWriter) {
        return this.stepBuilderFactory.get("authors")
                .<PostgresAuthor, Author>chunk(10)
                .reader(authorReader)
                .processor((ItemProcessor<PostgresAuthor, Author>) authorConverter::convert)
                .writer(authorWriter)
                .build();
    }

    @Bean
    public ItemReader<PostgresAuthor> authorReader(SessionFactory sessionFactory) {
        return new HibernateCursorItemReaderBuilder<PostgresAuthor>()
                .name("authorReader")
                .sessionFactory(sessionFactory)
                .queryString("from Author")
                .useStatelessSession(true)
                .build();
    }

    @Bean
    public ItemWriter<Author> authorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Author>()
                .collection("authors")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step processGenres(ItemReader<PostgresGenre> genreReader,
                              ItemWriter<Genre> genreWriter) {
        return this.stepBuilderFactory.get("genres")
                .<PostgresGenre, Genre>chunk(10)
                .reader(genreReader)
                .processor((ItemProcessor<PostgresGenre, Genre>) genreConverter::convert)
                .writer(genreWriter)
                .build();
    }

    @Bean
    public ItemReader<PostgresGenre> genreReader(SessionFactory sessionFactory) {
        return new HibernateCursorItemReaderBuilder<PostgresGenre>()
                .name("genreReader")
                .sessionFactory(sessionFactory)
                .queryString("from Genre")
                .useStatelessSession(true)
                .build();
    }

    @Bean
    public ItemWriter<Genre> genreWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Genre>()
                .collection("genres")
                .template(mongoTemplate)
                .build();
    }

}
