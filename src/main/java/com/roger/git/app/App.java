package com.roger.git.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScan("com.roger.git")
@EnableWebSecurity
public class App {

    public static final Logger LOGGER= LogManager.getLogger(App.class);

    /**
     * Launches the Spring Boot application
     *
     * Input param args - Application startup arguments
     */
    public static void main(String[] args) {
        LOGGER.info("Spring starting");
        ApplicationContext ctx = SpringApplication.run(App.class, args);
        LOGGER.info("Spring started");
    }

}
