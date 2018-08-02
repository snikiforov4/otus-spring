package ua.nykyforov.service.library.application.dao;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.core.dao.BookDao;
import ua.nykyforov.service.library.core.domain.Book;
import ua.nykyforov.service.library.core.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMapWithExpectedSize;

@Repository
public class JdbcBookDao implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public JdbcBookDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int insert(Book book) {
        String sql = "INSERT INTO book (title, genre_id) VALUES(:title, :genre_id)";
        Map<String, Object> params = newHashMapWithExpectedSize(2);
        params.put("title", book.getTitle());
        params.put("genre_id", book.getGenre().map(Genre::getId).orElse(null));
        return jdbc.update(sql, params);
    }

    @Override
    public Book getById(int id) {
        String sql = "SELECT b.id, b.title, b.genre_id, g.name as genre_name " +
                "FROM book b LEFT OUTER JOIN genre g ON b.genre_id = g.id WHERE b.id = :id";
        return jdbc.queryForObject(sql, ImmutableMap.of("id", id), new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet row, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(row.getInt("id"));
            book.setTitle(row.getString("title"));
            int genreId = row.getInt("genre_id");
            if (genreId > 0) {
                Genre genre = new Genre();
                genre.setId(genreId);
                genre.setName(row.getString("genre_name"));
                book.setGenre(genre);
            }
            return book;
        }
    }
}
