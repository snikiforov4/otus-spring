package ua.nykyforov.service.library.application.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.application.annotation.Jpa;
import ua.nykyforov.service.library.core.dao.BookDao;
import ua.nykyforov.service.library.core.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Jpa
@Repository
public class JpaBookDao implements BookDao {
    private static final Logger logger = LoggerFactory.getLogger(JpaBookDao.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    public Book getById(int id) {
        return em.find(Book.class, id);
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM Book b WHERE b.id = :id";
        int deletedCount = em.createQuery(query)
                .setParameter("id", id)
                .executeUpdate();
        logger.info("deleteById: id={} affectedRows={}", id, deletedCount);
    }

    public Collection<Book> findByTitleLike(String title) {
        String query = "SELECT b FROM Book b WHERE b.title LIKE CONCAT('%',:title,'%')";
        return em.createQuery(query, Book.class)
                .setParameter("title", title)
                .getResultList();
    }

}
