package ua.nykyforov.service.library.application.dao.jdbc;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.application.annotation.Jdbc;
import ua.nykyforov.service.library.core.dao.BookDao;
import ua.nykyforov.service.library.core.domain.Author;
import ua.nykyforov.service.library.core.domain.Book;
import ua.nykyforov.service.library.core.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMapWithExpectedSize;

@Jdbc
@Repository
public class JdbcBookDao implements BookDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcBookDao.class);

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public JdbcBookDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void insert(Book book) {
        String sql = "INSERT INTO usr.book (title, genre_id) VALUES(:title, :genre_id)";
        Map<String, Object> params = newHashMapWithExpectedSize(2);
        params.put("title", book.getTitle());
        params.put("genre_id", book.getGenre().map(Genre::getId).orElse(null));
        int updated = jdbc.update(sql, params);
        logger.info("insert: book={} affected={}", book, updated);
    }

    @Override
    public Book getById(int id) {
        String sql = "SELECT b.id, b.title, b.genre_id, g.name as genre_name, a.id as author_id, a.first_name as author_first_name, a.last_name as author_last_name " +
                "FROM usr.book b " +
                "LEFT OUTER JOIN usr.author_book ab ON b.id = ab.book_id " +
                "LEFT OUTER JOIN usr.author a ON a.id = ab.author_id " +
                "LEFT OUTER JOIN usr.genre g ON b.genre_id = g.id " +
                "WHERE b.id = :id";
        Map<Integer, Book> res = MapUtils.emptyIfNull(jdbc.query(sql, ImmutableMap.of("id", id), new BookExtractor()));
        if (res.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(
                    "found multiple rows in database", 1, res.size());
        }
        return res.get(id);
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM usr.book WHERE id = :id";
        int deleted = jdbc.update(sql, ImmutableMap.of("id", id));
        logger.info("deleteById: id={} affected={}", id, deleted);
    }

    @Override
    public Collection<Book> findByTitleLike(String title) {
        throw new NotImplementedException("Only implemented through JPA");
    }

    public Collection<Book> findByTitle(String query) {
        String sql = "SELECT b.id, b.title, b.genre_id, g.name as genre_name, a.id as author_id, a.first_name as author_first_name, a.last_name as author_last_name " +
                "FROM usr.book b " +
                "LEFT OUTER JOIN usr.author_book ab ON b.id = ab.book_id " +
                "LEFT OUTER JOIN usr.author a ON a.id = ab.author_id " +
                "LEFT OUTER JOIN usr.genre g ON b.genre_id = g.id " +
                "WHERE title LIKE :query";
        return MapUtils.emptyIfNull(jdbc.query(sql, ImmutableMap.of("query", query), new BookExtractor()))
                .values();
    }

    private static final class BookExtractor implements ResultSetExtractor<Map<Integer, Book>> {
        @Override
        public Map<Integer, Book> extractData(ResultSet rs) throws SQLException {
            Map<Integer, Book> result = Maps.newHashMap();
            while (rs.next()) {
                final int bookId = rs.getInt("id");
                final Book book = result.getOrDefault(bookId, new Book());
                if (result.containsKey(bookId)) {
                    addAuthorToBook(book, rs);
                } else {
                    book.setId(bookId);
                    book.setTitle(rs.getString("title"));
                    int genreId = rs.getInt("genre_id");
                    if (genreId > 0) {
                        Genre genre = new Genre();
                        genre.setId(genreId);
                        genre.setName(rs.getString("genre_name"));
                        book.setGenre(genre);
                    }
                    addAuthorToBook(book, rs);
                }
                result.put(bookId, book);
            }
            return result;
        }

        private void addAuthorToBook(Book book, ResultSet rs) throws SQLException {
            checkNotNull(book, "book");
            int authorId = rs.getInt("author_id");
            if (authorId > 0) {
                Author author = new Author();
                author.setId(authorId);
                author.setFirstName(rs.getString("author_first_name"));
                author.setLastName(rs.getString("author_last_name"));
                book.addAuthor(author);
            }
        }
    }

}
