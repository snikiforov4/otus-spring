package ua.nykyforov.service.library.application.dao;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.core.dao.BookDao;
import ua.nykyforov.service.library.core.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class JdbcBookDao implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public JdbcBookDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int insert(Book book) {
        String sql = "INSERT INTO book (title) VALUES(:title)";
        Map<String, String> params = ImmutableMap.of("title", book.getTitle());
        return jdbc.update(sql, params);
    }

    @Override
    public Book getById(int id) {
        String sql = "SELECT id, title FROM book WHERE id = :id";
        return jdbc.queryForObject(sql, ImmutableMap.of("id", id), new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet row, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(row.getInt("id"));
            book.setTitle(row.getString("title"));
            return book;
        }
    }
}
