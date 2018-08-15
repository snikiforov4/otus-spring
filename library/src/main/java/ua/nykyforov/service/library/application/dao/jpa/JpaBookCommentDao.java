package ua.nykyforov.service.library.application.dao.jpa;

import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.core.dao.BookCommentDao;
import ua.nykyforov.service.library.core.domain.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class JpaBookCommentDao implements BookCommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void insert(BookComment bookComment) {
        em.persist(bookComment);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<BookComment> getAllByBookId(int bookId) {
        return (List<BookComment>) em.createQuery("SELECT bc FROM BookComment bc WHERE bc.bookId = :bookId")
                .setParameter("bookId", bookId)
                .getResultList();
    }
}
