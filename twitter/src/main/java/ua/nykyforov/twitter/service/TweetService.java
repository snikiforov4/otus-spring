package ua.nykyforov.twitter.service;

import ua.nykyforov.twitter.domain.Tweet;

import java.util.Collection;

public interface TweetService {

    Collection<Tweet> findAll();

}
