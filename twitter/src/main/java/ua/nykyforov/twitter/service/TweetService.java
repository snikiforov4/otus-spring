package ua.nykyforov.twitter.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.domain.Tweet;

public interface TweetService {

    Flux<Tweet> findAll();

    Mono<Tweet> save(Tweet tweet);

    Mono<Tweet> findById(String id);

    Mono<Void> delete(Tweet tweet);
}
