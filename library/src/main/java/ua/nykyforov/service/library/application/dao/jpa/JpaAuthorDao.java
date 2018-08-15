package ua.nykyforov.service.library.application.dao.jpa;

import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.core.dao.AuthorDao;
import ua.nykyforov.service.library.core.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaAuthorDao implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public Author getById(int id) {
        return em.find(Author.class, id);
    }

}
