package ua.nykyforov.service.library.application.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.application.domain.BookComment;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootApplication
@SpringJUnitConfig(classes = {MongoConfig.class})
class BookCommentRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookCommentRepository sut;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(BookComment.class);
    }

    @Test
    void shouldSaveEntity() {
        final String bookId = "abc";
        final String commentText = "Awesome";
        BookComment savedBookComment = sut.save(new BookComment(commentText, bookId));

        assertThat(savedBookComment).isNotNull();
        final String id = savedBookComment.getId();
        assertThat(id).isNotNull();

        Optional<BookComment> bookComment = sut.findById(id);

        assertThat(bookComment)
                .isPresent()
                .hasValueSatisfying(bc -> {
                    assertThat(bc.getId()).isEqualTo(id);
                    assertThat(bc.getCreateDate()).isNotNull();
                    assertThat(bc.getText()).isEqualTo(commentText);
                    assertThat(bc.getBookId()).isEqualTo(bookId);
                });
    }

    @Test
    void shouldFindAllByBookId() {
        final String bookId = "abc";
        BookComment firstBookStub = new BookComment("Awesome", bookId);
        BookComment secondBookStub = new BookComment("Breathtaking", bookId);
        sut.save(firstBookStub);
        sut.save(secondBookStub);

        Collection<BookComment> bookComments = sut.findAllByBookId(bookId);

        assertThat(bookComments)
                .isNotNull()
                .hasSize(2)
                .usingElementComparatorOnFields("text", "bookId")
                .containsExactlyInAnyOrder(
                        firstBookStub,
                        secondBookStub
                );
    }

}