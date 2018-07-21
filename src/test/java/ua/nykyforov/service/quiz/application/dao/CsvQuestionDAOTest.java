package ua.nykyforov.service.quiz.application.dao;

import org.junit.jupiter.api.Test;
import ua.nykyforov.service.quiz.application.config.QuizConfig;
import ua.nykyforov.service.quiz.core.model.QuizQuestion;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CsvQuestionDAOTest {

    private CsvQuestionDAO sut;

    @Test
    void shouldReadAllQuestionsFromFile() {
        sut = new CsvQuestionDAO(createConfigWithPathToCsv("data/quiz-1.csv"));
        Collection<QuizQuestion> allQuestions = sut.getAllQuestions();

        assertThat(allQuestions).isNotNull();
        assertThat(allQuestions).hasSize(6);
    }

    @Test
    void shouldReadCorrectQuestionData() {
        sut = new CsvQuestionDAO(createConfigWithPathToCsv("data/quiz-2.csv"));
        Collection<QuizQuestion> allQuestions = sut.getAllQuestions();

        assertThat(allQuestions).isNotNull();
        assertThat(allQuestions).hasSize(1);
        QuizQuestion question = allQuestions.iterator().next();

        assertThat(question).isNotNull();
        assertThat(question.getAnswers()).hasSize(3);
        assertAll("Data read correctly",
                () -> assertThat(question.getQuestionText()).isEqualTo("q1"),
                () -> assertThat(question.getAnswers().get(0).getText()).isEqualTo("a1"),
                () -> assertFalse(question.getAnswers().get(0).isCorrect()),
                () -> assertThat(question.getAnswers().get(1).getText()).isEqualTo("a2"),
                () -> assertTrue(question.getAnswers().get(1).isCorrect()),
                () -> assertThat(question.getAnswers().get(2).getText()).isEqualTo("a3"),
                () -> assertFalse(question.getAnswers().get(2).isCorrect())
        );
    }

    private QuizConfig createConfigWithPathToCsv(String path) {
        QuizConfig quizConfig = new QuizConfig();
        quizConfig.setPathToCsv(path);
        return quizConfig;
    }

}