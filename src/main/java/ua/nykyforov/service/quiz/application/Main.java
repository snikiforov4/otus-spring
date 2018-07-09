package ua.nykyforov.service.quiz.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.nykyforov.service.quiz.application.controller.QuizController;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        QuizController quizController = ctx.getBean(QuizController.class);
        quizController.passTest();
    }

}
