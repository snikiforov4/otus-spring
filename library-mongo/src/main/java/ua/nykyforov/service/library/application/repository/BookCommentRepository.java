package ua.nykyforov.service.library.application.repository;

import org.springframework.data.repository.CrudRepository;
import ua.nykyforov.service.library.application.domain.BookComment;

import java.util.Collection;

public interface BookCommentRepository extends CrudRepository<BookComment, String> {

    Collection<BookComment> findAllByBookId(String bookId);

}
