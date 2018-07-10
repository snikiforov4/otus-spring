package ua.nykyforov.service.quiz.application.service;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.quiz.core.dao.QuestionDAO;
import ua.nykyforov.service.quiz.core.model.QuizQuestion;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleQuestionServiceTest {

    @Mock
    private QuestionDAO questionDAO;

    private SimpleQuestionService sut;

    @Nested
    @DisplayName("getAllQuestions")
    class GetAllQuestions {

        @Test
        void shouldReceiveQuestionsFromQuestionDao() {
            sut = new SimpleQuestionService(questionDAO);
            Collection<Object> questionsStub = Lists.newArrayList();
            doReturn(questionsStub).when(questionDAO).getAllQuestions();

            Collection<QuizQuestion> actualQuestions = sut.getAllQuestions();

            verify(questionDAO, times(1)).getAllQuestions();
            assertEquals(questionsStub, actualQuestions);
        }

    }
}