package ua.nykyforov.service.library.application.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.core.domain.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;


@JdbcTest
@SpringJUnitConfig(classes = {DataSourceConfig.class})
class JdbcGenreDaoTest {

    @Autowired
    private JdbcGenreDao sut;

    @Test
    void shouldCallGenreDao() {
        int updated = sut.insert(new Genre("Adventure"));

        assertEquals(1, updated, "wrong number of updated rows");
    }

}