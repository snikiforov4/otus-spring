package ua.nykyforov.twitter.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.domain.User;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findByUsername(String username);

}
