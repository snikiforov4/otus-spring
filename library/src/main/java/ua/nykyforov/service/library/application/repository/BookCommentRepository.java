package ua.nykyforov.service.library.application.repository;

import org.springframework.data.repository.CrudRepository;
import ua.nykyforov.service.library.core.domain.BookComment;

import java.util.Collection;

public interface BookCommentRepository extends CrudRepository<BookComment, Integer> {

    Collection<BookComment> findAllByBookId(int bookId);

}
