package ua.nykyforov.twitter.domain;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import ua.nykyforov.twitter.security.UserDetailsImpl;

import java.util.Collection;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field("username")
    private String username;
    @Field("pass")
    private String password;
    @Field("roles")
    private Collection<String> roles;

    public User() {
    }

    public User(String username, String password, Collection<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = ImmutableSet.copyOf(roles);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = ImmutableSet.copyOf(roles);
    }

    public UserDetails toUserDetails() {
        return new UserDetailsImpl(username, password,
                AuthorityUtils.createAuthorityList(roles.toArray(new String[0])));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("username", username)
                .add("password", password)
                .add("roles", roles)
                .toString();
    }
}
