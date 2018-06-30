package ua.nykyforov;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.nykyforov.service.QuestionService;
import ua.nykyforov.service.SimpleQuestionService;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        QuestionService questionService = ctx.getBean(SimpleQuestionService.class);
        System.out.println(questionService.getAllQuestions());
    }

}
