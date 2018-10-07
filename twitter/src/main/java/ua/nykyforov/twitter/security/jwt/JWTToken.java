package ua.nykyforov.twitter.security.jwt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JWTToken {
    @JsonProperty("token")
    private final String token;

    @JsonCreator
    public JWTToken(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
