package ua.nykyforov.service.quiz.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.quiz.application.config.QuizConfig;
import ua.nykyforov.service.quiz.core.application.QuestionService;
import ua.nykyforov.service.quiz.core.application.UserInteractionService;
import ua.nykyforov.service.quiz.core.model.QuizAnswer;
import ua.nykyforov.service.quiz.core.model.QuizQuestion;
import ua.nykyforov.service.quiz.core.model.QuizResult;
import ua.nykyforov.service.quiz.core.model.User;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class QuizController {
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuestionService questionService;
    private final UserInteractionService userInteractionService;
    private final QuizConfig quizConfig;

    @Autowired
    public QuizController(QuestionService questionService,
                          UserInteractionService userInteractionService,
                          QuizConfig quizConfig) {
        this.questionService = questionService;
        this.userInteractionService = userInteractionService;
        this.quizConfig = quizConfig;
    }

    public void passTest() {
        User user = userInteractionService.askUserInfo(quizConfig.getSettings().getLocale());
        logger.debug("{}", user);
        QuizResult quizResult = quiz();
        userInteractionService.sendQuizResult(user, quizResult, quizConfig.getSettings().getLocale());
    }

    private QuizResult quiz() {
        int correct = 0;
        Collection<QuizQuestion> allQuestions = questionService
                .getLimitNumberOfQuestions(quizConfig.getSettings().getNumberOfQuestions());
        for (QuizQuestion question : allQuestions) {
            List<QuizAnswer> answers = question.getAnswers();
            int answer = userInteractionService.askQuestion(
                    question.getQuestionText(),
                    answers.stream().map(QuizAnswer::getText).collect(toList()),
                    quizConfig.getSettings().getLocale());
            QuizAnswer chosenAnswer = answers.get(answer);
            logger.debug("{}", chosenAnswer);
            if (chosenAnswer.isCorrect()) correct++;
        }
        return new QuizResult(allQuestions.size(), correct);
    }

}
