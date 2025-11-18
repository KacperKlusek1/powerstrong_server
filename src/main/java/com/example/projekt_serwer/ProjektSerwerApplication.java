package com.example.projekt_serwer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.projekt_serwer")
public class ProjektSerwerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektSerwerApplication.class, args);
    }

}
