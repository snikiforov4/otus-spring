package ua.nykyforov.twitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.repository.TweetRepository;

import java.util.Collection;

@Service
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public Collection<Tweet> findAll() {
        return tweetRepository.findAll();
    }


}
