package ua.nykyforov.service.library.application.domain;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String title;

    @Nullable
    @DBRef
    @Field("genreId")
    private Genre genre;

    @Nullable
    @DBRef
    @Field("authors")
    private Collection<Author> authors;

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Optional<Genre> getGenre() {
        return Optional.ofNullable(genre);
    }

    public void setGenre(@Nullable Genre genre) {
        this.genre = genre;
    }

    public void addAuthor(Author author) {
        checkNotNull(author, "author");
        if (authors == null) {
            authors = Lists.newArrayList();
        }
        authors.add(author);
    }

    public Collection<Author> getAuthors() {
        return authors == null ? ImmutableList.of() : ImmutableList.copyOf(authors);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("title", title)
                .add("genre", genre)
                .add("authors", authors)
                .toString();
    }
}
