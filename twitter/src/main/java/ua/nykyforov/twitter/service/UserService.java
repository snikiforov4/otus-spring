package ua.nykyforov.twitter.service;

import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.domain.User;

public interface UserService {

    Mono<User> save(User tweet);

    Mono<User> findById(String id);

    Mono<User> findByUsername(String username);

}
