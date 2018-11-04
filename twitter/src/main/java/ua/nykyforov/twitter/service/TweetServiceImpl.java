package ua.nykyforov.twitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.repository.TweetRepository;

@Service
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Flux<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    @PreAuthorize("#tweet.userId == principal.userId")
    public Mono<Tweet> save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public Mono<Tweet> findById(String id) {
        return tweetRepository.findById(id);
    }

    @Override
    @PreAuthorize("#tweet.userId == principal.userId")
    public Mono<Void> delete(Tweet tweet) {
        return tweetRepository.delete(tweet);
    }
}
