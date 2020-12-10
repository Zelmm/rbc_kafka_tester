package com.example.rbc_kafka_tester.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("classpath:transactions.json")
    private Resource transactions;

    @Autowired
    public TransactionServiceImpl(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void produce() {
        String message = getTransactionsFromFile();
        log.info("<= sending {}", message);
        kafkaTemplate.send("rbc-transactions", message);
    }

    private String getTransactionsFromFile(){
        try {
            return Files.lines(Paths.get("src/main/resources/transactions.json")).map(String::trim).collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Read string from file failed.");
        }
    }
}
