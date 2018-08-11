package ua.nykyforov.service.library.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.library.core.application.GenreService;
import ua.nykyforov.service.library.core.dao.GenreDao;
import ua.nykyforov.service.library.core.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Mock
    private GenreDao genreDao;

    private GenreService sut;

    @BeforeEach
    void setUp() {
        sut = new GenreServiceImpl(genreDao);
    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        void shouldCallDao() {
            Genre genre = new Genre("Adventure");
            doNothing().when(genreDao).insert(refEq(genre));

            sut.save(genre);

            verify(genreDao, times(1)).insert(refEq(genre));
        }

    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        void shouldReturnOptionalWithPresentValueIfDaoReturnNonNullValue() {
            final int id = 42;
            Genre genre = new Genre("Adventure");
            doReturn(genre).when(genreDao).getById(eq(id));

            Optional<Genre> retGenre = sut.getById(id);

            verify(genreDao, times(1)).getById(eq(id));
            assertThat(retGenre)
                    .isPresent()
                    .containsSame(genre);
        }

        @Test
        void shouldReturnOptionalWithNotPresentValueIfDaoReturnNullValue() {
            final int id = 42;
            doReturn(null).when(genreDao).getById(eq(id));

            Optional<Genre> retGenre = sut.getById(id);

            verify(genreDao, times(1)).getById(eq(id));
            assertThat(retGenre).isNotPresent();
        }

    }

}