package ua.nykyforov.service.quiz.application;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class AppConfig {

    @Bean
    public static MessageSource appMessageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/l10n/app");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    public static MessageSource quizMessageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/l10n/quiz");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

}
