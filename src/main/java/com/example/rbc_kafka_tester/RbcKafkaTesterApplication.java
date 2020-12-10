package com.example.rbc_kafka_tester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class RbcKafkaTesterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RbcKafkaTesterApplication.class, args);
    }
}
