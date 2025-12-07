package pl.pollub.powerstrong_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "pl.pollub.powerstrong_server")
public class PowerstrongServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerstrongServerApplication.class, args);
    }

}
