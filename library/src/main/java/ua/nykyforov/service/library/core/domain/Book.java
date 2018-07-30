package ua.nykyforov.service.library.core.domain;

import com.google.common.base.MoreObjects;

import java.util.Optional;

public class Book {

    private Integer id;
    private String title;
    private Genre genre;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("title", title)
                .add("genre", genre)
                .toString();
    }
}
