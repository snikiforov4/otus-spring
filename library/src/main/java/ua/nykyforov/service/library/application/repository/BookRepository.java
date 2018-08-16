package ua.nykyforov.service.library.application.repository;

import org.springframework.data.repository.CrudRepository;
import ua.nykyforov.service.library.core.domain.Book;

import java.util.Collection;

public interface BookRepository extends CrudRepository<Book, Integer> {

    Collection<Book> findAllByTitleLike(String title);

}
