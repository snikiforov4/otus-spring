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

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

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

}