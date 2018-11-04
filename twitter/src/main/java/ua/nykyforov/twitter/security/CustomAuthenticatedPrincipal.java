package ua.nykyforov.twitter.security;

import com.google.common.base.MoreObjects;
import org.springframework.security.core.AuthenticatedPrincipal;

public class CustomAuthenticatedPrincipal implements AuthenticatedPrincipal {

    private final String userId;
    private final String username;

    public CustomAuthenticatedPrincipal(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", userId)
                .add("username", username)
                .toString();
    }

}
