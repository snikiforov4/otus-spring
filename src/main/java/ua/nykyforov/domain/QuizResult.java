package ua.nykyforov.domain;

public class QuizResult {

    private final int allQuestionsSize;
    private final int correctAnswers;

    public QuizResult(int allQuestionsSize, int correctAnswers) {
        this.allQuestionsSize = allQuestionsSize;
        this.correctAnswers = correctAnswers;
    }

    public int getAllQuestionsSize() {
        return allQuestionsSize;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public float getCorrectAnswersRatio() {
        return (float) correctAnswers / allQuestionsSize;
    }
}
