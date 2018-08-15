package ua.nykyforov.service.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.library.application.AuthorService;
import ua.nykyforov.service.library.dao.AuthorDao;
import ua.nykyforov.service.library.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorDao authorDao;

    private AuthorService sut;

    @BeforeEach
    void setUp() {
        sut = new AuthorServiceImpl(authorDao);
    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        void shouldCallDao() {
            Author author = createAuthorStub();
            doNothing().when(authorDao).insert(refEq(author));

            sut.save(author);

            verify(authorDao, times(1)).insert(refEq(author));
        }

    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        void shouldCallDao() {
            final int id = 42;
            Author author = createAuthorStub();
            doReturn(author).when(authorDao).getById(eq(id));

            Author authorRet = sut.getById(id);

            verify(authorDao, times(1)).getById(eq(id));
            assertThat(author).isSameAs(authorRet);
        }

    }

    private Author createAuthorStub() {
        return new Author("Stephen", "King");
    }

}