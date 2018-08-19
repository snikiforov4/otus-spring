package ua.nykyforov.service.library.application.domain;

import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "bookComments")
public class BookComment {

    @Id
    private String id;

    @Field("createDate")
    private Instant createDate;

    @Field("text")
    private String text;

    @Field("bookId")
    private String bookId;

    public BookComment() {
    }

    public BookComment(String text, String bookId) {
        this.createDate = Instant.now();
        this.text = text;
        this.bookId = bookId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
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
