package ua.nykyforov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.question.QuestionService;
import ua.nykyforov.service.quiz.domain.model.QuizAnswer;
import ua.nykyforov.service.quiz.domain.model.QuizQuestion;
import ua.nykyforov.service.quiz.domain.model.QuizResult;
import ua.nykyforov.service.quiz.domain.model.User;
import ua.nykyforov.service.user.UserInteractionService;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Service
public class QuizController {
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuestionService questionService;
    private final UserInteractionService userInteractionService;
    private final int defaultNumberOfQuestions;
    private final Locale defaultLocale;

    @Autowired
    public QuizController(QuestionService questionService,
                          UserInteractionService userInteractionService,
                          @Value("${quiz.defaultQuestions}") int defaultNumberOfQuestions,
                          @Value("${quiz.defaultLocale}") String defaultLocale) {
        this.questionService = questionService;
        this.userInteractionService = userInteractionService;
        this.defaultNumberOfQuestions = defaultNumberOfQuestions;
        this.defaultLocale = Locale.forLanguageTag(defaultLocale);
    }

    public void passTest() {
        User user = userInteractionService.askUserInfo(defaultLocale);
        logger.debug("{}", user);
        QuizResult quizResult = quiz();
        userInteractionService.sendQuizResult(user, quizResult, defaultLocale);
    }

    private QuizResult quiz() {
        int correct = 0;
        Collection<QuizQuestion> allQuestions = questionService.getLimitNumberOfQuestions(defaultNumberOfQuestions);
        for (QuizQuestion question : allQuestions) {
            List<QuizAnswer> answers = question.getAnswers();
            int answer = userInteractionService.askQuestion(question.getQuestionText(),
                    answers.stream().map(QuizAnswer::getText).collect(toList()), defaultLocale);
            QuizAnswer chosenAnswer = answers.get(answer);
            logger.debug("{}", chosenAnswer);
            if (chosenAnswer.isCorrect()) correct++;
        }
        return new QuizResult(allQuestions.size(), correct);
    }

}
