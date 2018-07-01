package ua.nykyforov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.domain.QuizAnswer;
import ua.nykyforov.domain.QuizQuestion;
import ua.nykyforov.domain.QuizResult;
import ua.nykyforov.domain.User;
import ua.nykyforov.service.question.QuestionService;
import ua.nykyforov.service.user.UserInteractionService;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class QuizController {
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuestionService questionService;
    private final UserInteractionService userInteractionService;

    @Autowired
    public QuizController(QuestionService questionService, UserInteractionService userInteractionService) {
        this.questionService = questionService;
        this.userInteractionService = userInteractionService;
    }

    public void passTest() {
        User user = userInteractionService.askUserInfo();
        logger.debug("{}", user);
        QuizResult quizResult = quiz();
        userInteractionService.sendQuizResult(user, quizResult);
    }

    private QuizResult quiz() {
        int correct = 0;
        Collection<QuizQuestion> allQuestions = questionService.getDefaultNumberOfQuestions();
        for (QuizQuestion question : allQuestions) {
            List<QuizAnswer> answers = question.getAnswers();
            int answer = userInteractionService.askQuestion(question.getQuestionText(),
                    answers.stream().map(QuizAnswer::getText).collect(toList()));
            QuizAnswer chosenAnswer = answers.get(answer);
            logger.debug("{}", chosenAnswer);
            if (chosenAnswer.isCorrect()) correct++;
        }
        return new QuizResult(allQuestions.size(), correct);
    }

}
