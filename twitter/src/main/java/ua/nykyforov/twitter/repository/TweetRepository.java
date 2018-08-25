package ua.nykyforov.twitter.repository;

import org.springframework.data.repository.CrudRepository;
import ua.nykyforov.twitter.domain.Tweet;

public interface TweetRepository extends CrudRepository<Tweet, String> {
}
