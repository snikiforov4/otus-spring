package ua.nykyforov.service.library.application.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.application.domain.Author;
import ua.nykyforov.service.library.application.domain.Book;
import ua.nykyforov.service.library.application.domain.Genre;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@SpringJUnitConfig(classes = {MongoConfig.class})
class BookRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository sut;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Book.class);
        mongoTemplate.dropCollection(Genre.class);
        mongoTemplate.dropCollection(Author.class);
    }

    @Test
    void shouldSaveEntity() {
        Genre genre = createAndSaveGenre("Horror");
        Book notPersistedBook = new Book("It");
        notPersistedBook.setGenre(genre);
        Author author = createAndSaveAuthor("Stephen", "King");
        notPersistedBook.addAuthor(author);
        Book savedBook = sut.save(notPersistedBook);

        assertThat(savedBook).satisfies(b -> {
            assertThat(b).isNotNull();
            assertThat(b.getId()).isNotBlank();
        });

        final String id = savedBook.getId();
        Optional<Book> book = sut.findById(id);

        assertThat(book)
                .isPresent()
                .hasValueSatisfying(b -> {
                    assertThat(b.getId()).isEqualTo(id);
                    assertThat(b.getTitle()).isEqualTo("It");
                    assertThat(b.getGenre()).isPresent();
                });
    }

    @Test
    void shouldFindAllBooksByTitleLike() {
        Book firstBookStub = createBook("Java Puzzlers");
        Book secondBookStub = createBook("Effective Java (3rd Edition)");
        sut.save(firstBookStub);
        sut.save(secondBookStub);

        Collection<Book> books = sut.findAllByTitleLike("*Java*");

        assertThat(books)
                .usingElementComparatorOnFields("id", "title")
                .containsExactlyInAnyOrder(
                        firstBookStub,
                        secondBookStub
                );
    }

    private Genre createAndSaveGenre(@SuppressWarnings("SameParameterValue") String genreName) {
        Genre genre = new Genre(genreName);
        mongoTemplate.save(genre);
        return genre;
    }

    @SuppressWarnings("SameParameterValue")
    private Author createAndSaveAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        mongoTemplate.save(author);
        return author;
    }

    private Book createBook(String title) {
        return new Book(title);
    }
}