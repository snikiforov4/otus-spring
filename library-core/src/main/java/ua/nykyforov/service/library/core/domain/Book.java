package ua.nykyforov.service.library.core.domain;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.Collection;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "book", schema = "usr")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name="genre_id")
    private Genre genre;

    @ManyToMany
    @JoinTable(name="author_book",
            joinColumns = @JoinColumn(name="book_id", referencedColumnName="ID"),
            inverseJoinColumns = @JoinColumn(name="author_id", referencedColumnName="ID")
    )
    private Collection<Author> authors;

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
