package ua.nykyforov.service.library.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.library.core.application.GenreService;
import ua.nykyforov.service.library.core.dao.GenreDao;
import ua.nykyforov.service.library.core.domain.Genre;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public void save(Genre genre) {
        int inserted = genreDao.insert(genre);
        checkArgument(inserted == 1, "affected number of rows: %s", inserted);
    }

    @Override
    public Genre getById(int id) { /// todo
        return null;
    }
}
