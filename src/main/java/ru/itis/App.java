package ru.itis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.configuration.ApplicationConfig;
import ru.itis.ui.MainMenuUI;

public class App {
    private static final MainMenuUI mainMenuUI;

    public static void main(String[] args) {
        mainMenuUI.render();
    }

    static {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        mainMenuUI = applicationContext.getBean(MainMenuUI.class);
    }
}
