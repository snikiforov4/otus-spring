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

import java.util.Collection;

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

    public QuizResult passTest() {
        int correctAnswers = 0;
        Collection<QuizQuestion> questionsForQuiz = getQuestionsForQuiz();
        for (QuizQuestion quizQuestion : questionsForQuiz) {
            int answer = askQuestion(quizQuestion);
            QuizAnswer chosenAnswer = quizQuestion.getAnswers().get(answer);
            logger.debug("{}", chosenAnswer);
            if (chosenAnswer.isCorrect()) correctAnswers++;
        }
        return new QuizResult(questionsForQuiz.size(), correctAnswers);
    }

    private Collection<QuizQuestion> getQuestionsForQuiz() {
        return questionService.getLimitNumberOfQuestions(quizConfig.getSettings().getNumberOfQuestions());
    }

    private int askQuestion(QuizQuestion quizQuestion) {
        return userInteractionService.askQuestion(
                quizQuestion.getQuestionText(),
                quizQuestion.getTextAnswers(),
                quizConfig.getSettings().getLocale());
    }

}
