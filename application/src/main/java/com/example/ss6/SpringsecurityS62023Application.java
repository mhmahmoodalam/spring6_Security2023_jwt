package com.example.ss6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.ss6.config")
public class SpringsecurityS62023Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityS62023Application.class, args);
    }

}
