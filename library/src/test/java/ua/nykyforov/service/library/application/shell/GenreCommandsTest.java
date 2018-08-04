package ua.nykyforov.service.library.application.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.library.core.application.GenreService;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GenreCommandsTest {

    @Mock
    private GenreService genreService;

    private GenreCommands sut;

    @BeforeEach
    void setUp() {
        sut = new GenreCommands(genreService);
    }

    @Nested
    @DisplayName("addGenre")
    class AddGenre {

        @Test
        void shouldPassSpecifiedGenreToService() {
            String genreName = "Horror";

            sut.addGenre(genreName);

            verify(genreService, times(1))
                    .save(argThat(argument -> genreName.equals(argument.getName())));
        }

    }

}