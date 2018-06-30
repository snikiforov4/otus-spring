package ua.nykyforov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.nykyforov.service.QuestionService;
import ua.nykyforov.service.SimpleQuestionService;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        QuestionService questionService = ctx.getBean(SimpleQuestionService.class);
        logger.info("{}", questionService.getAllQuestions());
    }

}
