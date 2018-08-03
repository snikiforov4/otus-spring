package ua.nykyforov.service.library.core.domain;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class Book {

    private Integer id;
    private String title;
    private Genre genre;
    private Collection<Author> authors;

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setGenre(Genre genre) {
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
