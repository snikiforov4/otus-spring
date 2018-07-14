package ua.nykyforov.service.quiz.application.dao;

import org.junit.jupiter.api.Test;
import ua.nykyforov.service.quiz.core.model.QuizQuestion;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CsvQuestionDAOTest {

    private CsvQuestionDAO sut;

    @Test
    void shouldReadAllQuestionsFromFile() {
        sut = new CsvQuestionDAO("data/quiz-1.csv");
        Collection<QuizQuestion> allQuestions = sut.getAllQuestions();

        assertNotNull(allQuestions);
        assertEquals(6, allQuestions.size());
    }

    @Test
    void shouldReadCorrectQuestionData() {
        sut = new CsvQuestionDAO("data/quiz-2.csv");
        Collection<QuizQuestion> allQuestions = sut.getAllQuestions();
        assertNotNull(allQuestions, "questions");
        assertEquals(1, allQuestions.size(), "only one question should be present");
        QuizQuestion question = allQuestions.iterator().next();
        assertNotNull(question, "question");
        assertAll("data read correctly",
                () -> assertEquals("q1", question.getQuestionText()),
                () -> assertEquals(3, question.getAnswers().size()),
                () -> assertEquals("a1", question.getAnswers().get(0).getText()),
                () -> assertFalse(question.getAnswers().get(0).isCorrect()),
                () -> assertEquals("a2", question.getAnswers().get(1).getText()),
                () -> assertTrue(question.getAnswers().get(1).isCorrect()),
                () -> assertEquals("a3", question.getAnswers().get(2).getText()),
                () -> assertFalse(question.getAnswers().get(2).isCorrect())
        );

    }

}