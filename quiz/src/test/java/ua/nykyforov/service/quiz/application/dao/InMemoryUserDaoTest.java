package ua.nykyforov.service.quiz.application.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ua.nykyforov.service.quiz.core.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

class InMemoryUserDaoTest {

    private InMemoryUserDao sut;

    @BeforeEach
    void setUp() {
        sut = new InMemoryUserDao();
    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        void shouldSetIdAndReturnUser() {
            User user = new User("John", "Smith");
            User retUser = sut.save(user);

            assertThat(retUser).isNotNull();
            assertSame(user, retUser);
            assertThat(retUser.getId()).isNotNull();
        }

    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        void shouldReturnSavedUser() {
            User user = sut.save(new User("John", "Smith"));
            Long userId = user.getId();
            assert userId != null;

            User retUser = sut.getById(userId);

            assertThat(retUser).isNotNull();
            assertSame(user, retUser);
            assertThat(retUser.getId()).isEqualTo(userId);
        }

    }

}