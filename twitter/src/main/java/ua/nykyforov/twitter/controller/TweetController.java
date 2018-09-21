package ua.nykyforov.twitter.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.dto.TweetDto;
import ua.nykyforov.twitter.service.TweetService;

import static com.google.common.base.Preconditions.checkArgument;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public Flux<TweetDto> getAllTweets() {
        return tweetService.findAll()
                .map(Tweet::toDto);
    }

    @PostMapping
    public Mono<TweetDto> saveNewTweet(@RequestBody TweetDto tweetDto) {
        return tweetService.save(new Tweet(tweetDto.getText()))
                .map(Tweet::toDto);
    }

    @PutMapping
    public Mono<TweetDto> updateTweet(@RequestBody TweetDto tweetDto) {
        checkArgument(StringUtils.isNotBlank(tweetDto.getId()), "id is blank");
        return tweetService.findById(tweetDto.getId())
                .doOnNext(t -> t.setText(tweetDto.getText()))
                .flatMap(tweetService::save)
                .map(Tweet::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTweet(@PathVariable("id") String id) {
        return tweetService.deleteById(id);
    }

}
