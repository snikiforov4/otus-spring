package ua.nykyforov.twitter.service;

import ua.nykyforov.twitter.domain.Tweet;

import java.util.Collection;
import java.util.Optional;

public interface TweetService {

    Collection<Tweet> findAll();

    Tweet save(Tweet tweet);

    Optional<Tweet> findById(String id);

    void deleteById(String id);

}
