package ua.nykyforov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nykyforov.domain.QuizResult;
import ua.nykyforov.domain.Question;
import ua.nykyforov.domain.User;
import ua.nykyforov.service.question.QuestionService;
import ua.nykyforov.service.UserInteractionService;

public class QuizController {
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuestionService questionService;
    private final UserInteractionService userInteractionService;

    public QuizController(QuestionService questionService, UserInteractionService userInteractionService) {
        this.questionService = questionService;
        this.userInteractionService = userInteractionService;
    }

    public QuizResult passTest() {
        User user = userInteractionService.askUserInfo();
        logger.info("User={}", user.getFullName());
        for (Question question : questionService.getAllQuestions()) {
            String answer = userInteractionService.askQuestion(question.getQuestion(), question.getAnswers());
            logger.info("answer={}", answer);
        }
        return null;
    }

}
