package ua.nykyforov.service.quiz.core.model;

import javax.annotation.concurrent.Immutable;

@Immutable
public class QuizResult {

    private final int numberOfQuestions;
    private final int correctAnswers;

    public QuizResult(int numberOfQuestions, int correctAnswers) {
        this.numberOfQuestions = numberOfQuestions;
        this.correctAnswers = correctAnswers;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public float getCorrectAnswersRatio() {
        return (float) correctAnswers / numberOfQuestions;
    }
}
