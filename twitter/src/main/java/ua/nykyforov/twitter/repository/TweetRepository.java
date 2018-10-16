package ua.nykyforov.twitter.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ua.nykyforov.twitter.domain.Tweet;

public interface TweetRepository extends ReactiveCrudRepository<Tweet, String> {

    @SuppressWarnings("NullableProblems")
    @Override
    Flux<Tweet> findAll();

}
