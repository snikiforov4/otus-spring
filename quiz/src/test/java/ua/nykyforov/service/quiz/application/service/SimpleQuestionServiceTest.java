package ua.nykyforov.service.quiz.application.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.quiz.core.dao.QuestionDao;
import ua.nykyforov.service.quiz.core.model.QuizQuestion;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleQuestionServiceTest {

    @Mock
    private QuestionDao questionDao;

    private SimpleQuestionService sut;

    @BeforeEach
    void setUp() {
        sut = new SimpleQuestionService(questionDao);
    }

    @Nested
    @DisplayName("getAllQuestions")
    class GetAllQuestions {

        @Test
        void shouldReceiveQuestionsFromQuestionDao() {
            Collection<Object> questionsStub = Lists.newArrayList();
            doReturn(questionsStub).when(questionDao).getAllQuestions();

            Collection<QuizQuestion> actualQuestions = sut.getAllQuestions();

            verify(questionDao, times(1)).getAllQuestions();
            assertSame(questionsStub, actualQuestions);
        }

    }

    @Nested
    @DisplayName("getLimitNumberOfQuestions")
    class GetLimitNumberOfQuestions {

        @Test
        void shouldReceiveQuestionsFromQuestionDao() {
            final int limit = 5;
            Collection<Object> questionsStub = Lists.newArrayList();
            doReturn(questionsStub).when(questionDao).getAllQuestions();

            Collection<QuizQuestion> actualQuestions = sut.getLimitNumberOfQuestions(limit);

            verify(questionDao, times(1)).getAllQuestions();
            assertSame(questionsStub, actualQuestions);
        }

        @ParameterizedTest
        @CsvSource({
                "3, 3",
                "5, 5",
                "7, 5"
        })
        void shouldReturnLimitedNumberOfQuestionsByParameter(final int actualSize, final int expectedSize) {
            final int limit = 5;
            Collection<Object> questionsStub = createListWithNumberOfQuestions(actualSize);
            doReturn(questionsStub).when(questionDao).getAllQuestions();

            Collection<QuizQuestion> actualQuestions = sut.getLimitNumberOfQuestions(limit);

            assertThat(actualQuestions).isNotNull();
            assertThat(actualQuestions).hasSize(expectedSize);
        }

        private List<Object> createListWithNumberOfQuestions(int numberOfQuestions) {
            ImmutableList.Builder<Object> b = ImmutableList.builder();
            for (int i = 0; i < numberOfQuestions; i++) {
                b.add(new Object());
            }
            return b.build();
        }

    }

}