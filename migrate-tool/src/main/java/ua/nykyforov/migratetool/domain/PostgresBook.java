package ua.nykyforov.migratetool.domain;


import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Collection;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity(name = "Book")
@Table(name = "book", schema = "usr")
public class PostgresBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="genre_id")
    private PostgresGenre genre;

    @Nullable
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="author_book", schema = "usr",
            joinColumns = @JoinColumn(name="book_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="author_id", referencedColumnName="id")
    )
    private Collection<PostgresAuthor> authors;

    public PostgresBook() {
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

    public Optional<PostgresGenre> getGenre() {
        return Optional.ofNullable(genre);
    }

    public void setGenre(@Nullable PostgresGenre genre) {
        this.genre = genre;
    }

    public void addAuthor(PostgresAuthor author) {
        checkNotNull(author, "author");
        if (authors == null) {
            authors = Lists.newArrayList();
        }
        authors.add(author);
    }

    public Collection<PostgresAuthor> getAuthors() {
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