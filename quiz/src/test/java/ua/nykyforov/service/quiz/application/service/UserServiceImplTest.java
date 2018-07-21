package ua.nykyforov.service.quiz.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.quiz.core.dao.UserDAO;
import ua.nykyforov.service.quiz.core.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    private UserServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new UserServiceImpl(userDAO);
    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        void shouldPassUserToUserDao() {
            User user = new User("John", "Smith");
            sut.save(user);

            verify(userDAO, times(1)).save(refEq(user));
        }

    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        void shouldGetUserFromUserDao() {
            final long id = 42L;
            User user = new User("John", "Smith");
            doReturn(user).when(userDAO).getById(eq(id));

            Optional<User> retUser = sut.getById(id);

            assertThat(retUser).isPresent();
            verify(userDAO, times(1)).getById(eq(id));
            assertThat(retUser).containsSame(user);
        }

        @Test
        void shouldReturnEmptyOptionalIfUserDaoReturnNull() {
            final long id = 44L;
            Optional<User> retUser = sut.getById(id);

            assertThat(retUser).isNotPresent();
            verify(userDAO, times(1)).getById(eq(id));
        }

    }

}