package ua.nykyforov.domain;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkNotNull;

public class User {
    private final String firstName;
    private final String lastName;

    public User(String firstName, String lastName) {
        this.firstName = checkNotNull(firstName, "firstName");
        this.lastName = checkNotNull(lastName, "lastName");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .toString();
    }
}
