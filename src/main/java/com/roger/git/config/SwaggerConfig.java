package com.roger.git.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.roger.git.util.GitConstant.DEFAULT_API_INFO;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Configuration class for  Swagger
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket productApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO).select()
                .apis(RequestHandlerSelectors.basePackage("com.roger.git.controller"))
                .paths(regex("/api.*"))
                .build();
    }
}
