package ua.nykyforov.service.library.application.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.library.core.application.BookCommentService;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookCommentCommandsTest {

    @Mock
    private BookCommentService bookCommentService;

    private BookCommentCommands sut;

    @BeforeEach
    void setUp() {
        sut = new BookCommentCommands(bookCommentService);
    }

    @Nested
    @DisplayName("addCommentToBook")
    class AddCommentToBook {

        @Test
        void shouldPassBookCommentToService() {
            String comment = "Superb!11!";
            int bookId = 42;

            sut.addCommentToBook(comment, bookId);

            verify(bookCommentService, times(1))
                    .save(argThat(argument -> argument != null
                            && argument.getBookId() == bookId
                            && Objects.equals(argument.getText(), comment)
                            && argument.getCreateDate() != null
                    ));
        }

    }

    @Nested
    @DisplayName("getAllBookCommentsByBookId")
    class GetAllBookCommentsByBookId {

        @Test
        void shouldCallServiceMethod() {
            int bookId = 42;

            sut.getAllBookCommentsByBookId(bookId);

            verify(bookCommentService, times(1)).getAllByBookId(eq(bookId));
        }

    }

}