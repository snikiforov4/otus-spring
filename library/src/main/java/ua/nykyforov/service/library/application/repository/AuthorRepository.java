package ua.nykyforov.service.library.application.repository;

import org.springframework.data.repository.CrudRepository;
import ua.nykyforov.service.library.core.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
}
