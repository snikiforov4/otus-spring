package ua.nykyforov.twitter.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.dto.TweetDto;
import ua.nykyforov.twitter.service.TweetService;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/tweet")
public class TweetController {
    private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public Collection<TweetDto> getAllTweets() {
        return tweetService.findAll()
                .stream()
                .map(Tweet::toDto)
                .collect(toList());
    }

    @PostMapping
    public TweetDto saveNewTweet(@RequestBody TweetDto tweetDto) {
        Tweet tweet = tweetService.save(new Tweet(tweetDto.getText()));
        logger.info("Saved tweet: {}", tweet);
        return tweet.toDto();
    }

    @PutMapping
    public TweetDto updateTweet(@RequestBody TweetDto tweetDto) {
        checkArgument(StringUtils.isNotBlank(tweetDto.getId()), "id is blank");
        Tweet tweet = tweetService.findById(tweetDto.getId()).orElseThrow(NotFoundException::new);
        tweet.setText(tweetDto.getText());
        tweetService.save(tweet);
        return tweet.toDto();
    }

    @GetMapping("/delete/{id}")
    public String deleteTweet(@PathVariable("id") String id) {
        tweetService.deleteById(id);
        return "redirect:/";
    }

}
