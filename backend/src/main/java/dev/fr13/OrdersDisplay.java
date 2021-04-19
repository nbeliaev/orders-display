package dev.fr13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class OrdersDisplay {

    public static void main(String[] args) {
        SpringApplication.run(OrdersDisplay.class, args);
    }
}
