package ua.nykyforov.twitter.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class JwtToken {
    @JsonProperty("token")
    private final String token;

    @JsonCreator
    public JwtToken(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
