package ua.nykyforov.service.quiz.application.controller;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.quiz.application.config.QuizConfig;
import ua.nykyforov.service.quiz.core.application.QuestionService;
import ua.nykyforov.service.quiz.core.application.UserInteractionService;
import ua.nykyforov.service.quiz.core.model.QuizAnswer;
import ua.nykyforov.service.quiz.core.model.QuizQuestion;
import ua.nykyforov.service.quiz.core.model.QuizResult;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizControllerImplTest {

    private static final Locale LOCALE = Locale.ENGLISH;
    private static final int NUMBER_OF_QUESTIONS = 3;

    @Mock
    private QuestionService questionService;
    @Mock
    private UserInteractionService userInteractionService;

    private QuizControllerImpl sut;

    @Nested
    @DisplayName("passTest")
    class PassTest {

        @Test
        void shouldGetQuestionsFromQuestionService() {
            sut = new QuizControllerImpl(questionService, userInteractionService, buildQuizConfig());
            doReturn(buildQuizQuestions()).when(questionService).getLimitNumberOfQuestions(eq(NUMBER_OF_QUESTIONS));

            sut.passTest();

            verify(questionService, times(1))
                    .getLimitNumberOfQuestions(eq(NUMBER_OF_QUESTIONS));
        }

        @Test
        void shouldCallAskQuestionMethodAsManyAsQuestionsSize() {
            sut = new QuizControllerImpl(questionService, userInteractionService, buildQuizConfig());
            doReturn(buildQuizQuestions()).when(questionService).getLimitNumberOfQuestions(eq(NUMBER_OF_QUESTIONS));

            sut.passTest();

            verify(userInteractionService, times(NUMBER_OF_QUESTIONS))
                    .askQuestion(anyString(), anyList(), refEq(LOCALE));
        }

        @ParameterizedTest
        @CsvSource({
                "0, 1, 1, 1",
                "3, 0, 0, 0",
                "1, 1, 1, 0"
        })
        void shouldReturnCorrectQuizResult(int expectedCorrectAnswers, int answer1, int answer2, int answer3) {
            sut = new QuizControllerImpl(questionService, userInteractionService, buildQuizConfig());
            doReturn(buildQuizQuestions()).when(questionService).getLimitNumberOfQuestions(eq(NUMBER_OF_QUESTIONS));
            doReturn(answer1, answer2, answer3)
                    .when(userInteractionService).askQuestion(anyString(), anyList(), eq(LOCALE));

            QuizResult quizResult = sut.passTest();

            assertThat(quizResult).isNotNull();
            assertThat(quizResult.getNumberOfQuestions()).isEqualTo(NUMBER_OF_QUESTIONS);
            assertThat(quizResult.getCorrectAnswers()).isEqualTo(expectedCorrectAnswers);
        }

        private QuizConfig buildQuizConfig() {
            QuizConfig cfg = new QuizConfig();
            QuizConfig.Settings settings = new QuizConfig.Settings();
            settings.setLocale(LOCALE);
            settings.setNumberOfQuestions(NUMBER_OF_QUESTIONS);
            cfg.setSettings(settings);
            return cfg;
        }

        private List<QuizQuestion> buildQuizQuestions() {
            ImmutableList<QuizQuestion> result = ImmutableList.of(
                    new QuizQuestion("q1",
                            ImmutableList.of(QuizAnswer.correct("a11"), QuizAnswer.incorrect("a12"))),
                    new QuizQuestion("q2",
                            ImmutableList.of(QuizAnswer.correct("a21"), QuizAnswer.incorrect("a22"))),
                    new QuizQuestion("q3",
                            ImmutableList.of(QuizAnswer.correct("a31"), QuizAnswer.incorrect("a32")))
            );
            assert result.size() == NUMBER_OF_QUESTIONS;
            return result;
        }

    }

}