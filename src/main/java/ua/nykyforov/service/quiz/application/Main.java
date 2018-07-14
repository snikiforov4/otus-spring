package ua.nykyforov.service.quiz.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ua.nykyforov.service.quiz.application.controller.QuizController;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        QuizController quizController = ctx.getBean(QuizController.class);
        quizController.passTest();
    }

}
