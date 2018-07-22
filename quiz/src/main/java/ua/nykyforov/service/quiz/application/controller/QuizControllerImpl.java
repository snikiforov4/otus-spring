package ua.nykyforov.service.quiz.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.quiz.application.config.QuizConfig;
import ua.nykyforov.service.quiz.core.application.QuestionService;
import ua.nykyforov.service.quiz.core.application.QuizController;
import ua.nykyforov.service.quiz.core.application.UserInteractionService;
import ua.nykyforov.service.quiz.core.model.QuizAnswer;
import ua.nykyforov.service.quiz.core.model.QuizQuestion;
import ua.nykyforov.service.quiz.core.model.QuizResult;
import ua.nykyforov.service.quiz.core.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Service
public class QuizControllerImpl implements QuizController {
    private static final Logger logger = LoggerFactory.getLogger(QuizControllerImpl.class);

    private final QuestionService questionService;
    private final UserInteractionService userInteractionService;
    private final QuizConfig quizConfig;

    @Autowired
    public QuizControllerImpl(QuestionService questionService,
                              UserInteractionService userInteractionService,
                              QuizConfig quizConfig) {
        this.questionService = questionService;
        this.userInteractionService = userInteractionService;
        this.quizConfig = quizConfig;
    }

    public void passTest(User user) {
        Locale locale = quizConfig.getSettings().getLocale();
        QuizResult quizResult = quiz();
        userInteractionService.sendQuizResult(user, quizResult, locale);
    }

    private QuizResult quiz() {
        int correctAnswers = 0;
        Collection<QuizQuestion> allQuestions = questionService
                .getLimitNumberOfQuestions(quizConfig.getSettings().getNumberOfQuestions());
        for (QuizQuestion quizQuestion : allQuestions) {
            List<QuizAnswer> answers = quizQuestion.getAnswers();
            int answer = userInteractionService.askQuestion(
                    quizQuestion.getQuestionText(),
                    quizQuestion.getTextAnswers(),
                    quizConfig.getSettings().getLocale());
            QuizAnswer chosenAnswer = answers.get(answer);
            logger.debug("{}", chosenAnswer);
            if (chosenAnswer.isCorrect()) correctAnswers++;
        }
        return new QuizResult(allQuestions.size(), correctAnswers);
    }

}
