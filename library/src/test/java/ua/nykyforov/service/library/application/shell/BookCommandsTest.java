package ua.nykyforov.service.library.application.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.library.core.application.BookService;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookCommandsTest {

    @Mock
    private BookService bookService;

    private BookCommands sut;

    @BeforeEach
    void setUp() {
        sut = new BookCommands(bookService);
    }

    @Nested
    @DisplayName("addBook")
    class AddBook {

        @Test
        void shouldWrapParameterToEntityAndPassToService() {
            String title = "It";

            sut.addBook(title);

            verify(bookService, times(1))
                    .save(argThat(argument -> Objects.equals(argument.getTitle(), title)));
        }

    }

}