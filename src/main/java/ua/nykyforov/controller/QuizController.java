package ua.nykyforov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nykyforov.domain.QuizAnswer;
import ua.nykyforov.domain.QuizQuestion;
import ua.nykyforov.domain.QuizResult;
import ua.nykyforov.domain.User;
import ua.nykyforov.service.question.QuestionService;
import ua.nykyforov.service.user.UserInteractionService;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
        logger.info("User={}", user.getName());
        for (QuizQuestion question : questionService.getAllQuestions()) {
            List<QuizAnswer> answers = question.getAnswers();
            int answer = userInteractionService.askQuestion(question.getQuestionText(),
                    answers.stream().map(QuizAnswer::getText).collect(toList()));
            logger.info("answer={}", answers.get(answer).getText());
        }
        return null;
    }

}
