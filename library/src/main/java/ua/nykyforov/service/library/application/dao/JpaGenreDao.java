package ua.nykyforov.service.library.application.dao;

import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.core.dao.GenreDao;
import ua.nykyforov.service.library.core.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaGenreDao implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Genre genre) {
        em.persist(genre);
    }

    @Override
    public Genre getById(int id) {
        return em.find(Genre.class, id);
    }

    public long count() {
        return em.createQuery("SELECT COUNT(0) FROM Genre", Long.class)
                .getSingleResult();
    }

}
