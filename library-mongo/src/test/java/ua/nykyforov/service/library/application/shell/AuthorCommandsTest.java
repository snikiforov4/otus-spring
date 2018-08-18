package ua.nykyforov.service.library.application.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.library.application.repository.AuthorRepository;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorCommandsTest {

    @Mock
    private AuthorRepository authorRepository;

    private AuthorCommands sut;

    @BeforeEach
    void setUp() {
        sut = new AuthorCommands(authorRepository);
    }

    @Nested
    @DisplayName("addAuthor")
    class AddAuthor {

        @Test
        void shouldCallRepositoryMethodWithCorrectParam() {
            String firstName = "Stephen";
            String lastName = "King";

            sut.addAuthor(firstName, lastName);

            verify(authorRepository, times(1))
                    .save(argThat(argument -> Objects.equals(firstName, argument.getFirstName())
                            && Objects.equals(lastName, argument.getLastName())));
        }

    }

}