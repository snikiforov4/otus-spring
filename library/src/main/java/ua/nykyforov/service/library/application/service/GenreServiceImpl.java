package ua.nykyforov.service.library.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.library.core.application.GenreService;
import ua.nykyforov.service.library.core.dao.GenreDao;
import ua.nykyforov.service.library.core.domain.Genre;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    @Transactional
    public void save(Genre genre) {
        genreDao.insert(genre);
    }

    @Override
    @Transactional
    public Optional<Genre> getById(int id) {
        return Optional.ofNullable(genreDao.getById(id));
    }
}
