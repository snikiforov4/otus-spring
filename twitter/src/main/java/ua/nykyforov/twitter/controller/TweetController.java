package ua.nykyforov.twitter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TweetController {

    @GetMapping("/")
    public String getAllTweetsPage(Model model) {
        return "index";
    }

}
