package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import ua.nykyforov.service.library.core.application.BookCommentService;
import ua.nykyforov.service.library.core.domain.BookComment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;

@ShellComponent
@SuppressWarnings("UnusedReturnValue")
public class BookCommentCommands {

    private final BookCommentService bookCommentService;

    @Autowired
    public BookCommentCommands(BookCommentService bookCommentService) {
        this.bookCommentService = bookCommentService;
    }

    @ShellMethod("Add comment to book by book ID.")
    BookComment addCommentToBook(@NotBlank String comment, @Positive int bookId) {
        BookComment bookComment = new BookComment(comment, bookId);
        bookCommentService.save(bookComment);
        return bookComment;
    }

    @ShellMethod("Get all book comments by book ID.")
    Table getAllBookCommentsByBookId(@Positive int bookId) {
        List<BookComment> allComments = bookCommentService.getAllByBookId(bookId);
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
