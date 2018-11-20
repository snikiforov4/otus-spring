package ua.nykyforov.migratetool.domain;

import com.google.common.base.MoreObjects;

import javax.persistence.*;

@Entity(name = "Genre")
@Table(name = "genre", schema = "usr")
public class PostgresGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    public PostgresGenre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("name", name)
                .toString();
    }
}
