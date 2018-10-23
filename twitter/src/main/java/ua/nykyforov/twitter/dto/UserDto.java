package ua.nykyforov.twitter.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserDto {

    @JsonProperty("username")
    private final String username;
    @JsonProperty("password")
    private final String password;

    @JsonCreator
    public UserDto(@JsonProperty("username") String username,
                   @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
