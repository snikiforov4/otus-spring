package ua.nykyforov.twitter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.service.TweetService;

import java.util.Collection;

@Controller
public class TweetController {
    private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/")
    public String getAllTweets(Model model) {
        Collection<Tweet> tweets = tweetService.findAll();
        model.addAttribute("tweets", tweets);
        return "index";
    }

    @GetMapping("/add")
    public String addPage() {
        return "add";
    }

    @PostMapping("/add")
    public String saveNewTweet(@RequestParam("text") String tweetText) {
        Tweet tweet = tweetService.save(new Tweet(tweetText));
        logger.info("Saved tweet: {}", tweet);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") String id, Model model) {
        Tweet tweet = tweetService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("tweet", tweet);
        return "edit";
    }

    @PostMapping("/edit")
    public String updateTweet(@RequestParam("id") String id, @RequestParam("text") String tweetText) {
        Tweet tweet = tweetService.findById(id).orElseThrow(NotFoundException::new);
        tweet.setText(tweetText);
        tweetService.save(tweet);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTweet(@PathVariable("id") String id) {
        tweetService.deleteById(id);
        return "redirect:/";
    }

}
