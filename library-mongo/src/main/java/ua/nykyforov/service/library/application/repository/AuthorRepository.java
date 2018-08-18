package ua.nykyforov.service.library.application.repository;

import org.springframework.data.repository.CrudRepository;
import ua.nykyforov.service.library.application.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, String> {
}
