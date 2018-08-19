package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import ua.nykyforov.service.library.application.domain.BookComment;
import ua.nykyforov.service.library.application.repository.BookCommentRepository;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@ShellComponent
@SuppressWarnings("UnusedReturnValue")
public class BookCommentCommands {

    private final BookCommentRepository bookCommentRepository;

    @Autowired
    public BookCommentCommands(BookCommentRepository bookCommentRepository) {
        this.bookCommentRepository = bookCommentRepository;
    }

    @ShellMethod("Add comment to book by book ID.")
    public BookComment addCommentToBook(@NotBlank String comment, @NotBlank String bookId) {
        BookComment bookComment = new BookComment(comment, bookId);
        return bookCommentRepository.save(bookComment);
    }

    @ShellMethod("Get all book comments by book ID.")
    public Table getAllBookCommentsByBookId(@NotBlank String bookId) {
        Collection<BookComment> allComments = bookCommentRepository.findAllByBookId(bookId);
        return buildBooksComments(allComments);
    }

    private Table buildBooksComments(Collection<BookComment> bookComments) {
        Object[][] data = new Object[bookComments.size() + 1][];
        int idx = 0;
        data[idx++] = new String[]{"ID", "Date", "Comment", "Book ID"};
        for (BookComment bookComment : bookComments) {
            data[idx++] = new Object[] {
                    bookComment.getId(),
                    bookComment.getCreateDate().toString(),
                    bookComment.getText(),
                    bookComment.getBookId()
            };
        }
        return new TableBuilder(new ArrayTableModel(data))
                .addHeaderAndVerticalsBorders(BorderStyle.oldschool)
                .on((row, column, model) -> column == 2).addSizer(new AbsoluteWidthSizeConstraints(25))
                .build();
    }
}
