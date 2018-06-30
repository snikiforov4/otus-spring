package ua.nykyforov.domain;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkNotNull;

public class User {
    private final String name;

    public User(String name) {
        this.name = checkNotNull(name, "name");
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .toString();
    }
}
