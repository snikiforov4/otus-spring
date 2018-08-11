package ua.nykyforov.service.library.core.domain;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "book_comment")
public class BookComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "create_date")
    private Instant createDate;
    @Column(name = "text")
    private String text;
    @Column(name = "book_id")
    private int bookId;

    public BookComment() {
    }

    public BookComment(String text, int bookId) {
        this.createDate = Instant.now();
        this.text = text;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("createDate", createDate)
                .add("text", text)
                .add("bookId", bookId)
                .toString();
    }
}
