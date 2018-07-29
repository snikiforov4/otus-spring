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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
            doReturn(1).when(genreDao).insert(refEq(genre));

            sut.save(genre);

            verify(genreDao, times(1)).insert(refEq(genre));
        }

        @Test
        void shouldThrowExceptionIfDidNotReturnResultEqualsOne() {
            Genre genre = new Genre("Adventure");
            doReturn(0).when(genreDao).insert(refEq(genre));

            assertThatThrownBy(() -> sut.save(genre))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("affected number of rows: 0");
        }

    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        void shouldCallDao() {
            final int id = 42;
            Genre genre = new Genre("Adventure");
            doReturn(genre).when(genreDao).getById(eq(id));

            Genre retGenre = sut.getById(id);

            verify(genreDao, times(1)).getById(eq(id));
            assertThat(genre).isSameAs(retGenre);
        }

    }

}