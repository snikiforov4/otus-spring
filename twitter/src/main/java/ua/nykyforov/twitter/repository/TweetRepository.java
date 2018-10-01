package ua.nykyforov.twitter.repository;

import org.springframework.data.repository.CrudRepository;
import ua.nykyforov.twitter.domain.Tweet;

import java.util.Collection;

public interface TweetRepository extends CrudRepository<Tweet, String> {

    @SuppressWarnings("NullableProblems")
    @Override
    Collection<Tweet> findAll();

}
