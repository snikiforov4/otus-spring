package ua.nykyforov.service.library.application.repository;

import org.springframework.data.repository.CrudRepository;
import ua.nykyforov.service.library.core.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Integer> {
}
