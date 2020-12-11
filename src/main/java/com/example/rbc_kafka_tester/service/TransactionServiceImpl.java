package com.example.rbc_kafka_tester.service;

import com.example.rbc_kafka_tester.dto.TransactionDTO;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final KafkaTemplate<Long, List<TransactionDTO>> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("classpath:transactions.json")
    private Resource transactions;

    @Autowired
    public TransactionServiceImpl(KafkaTemplate<Long, List<TransactionDTO>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void produce() {
        var dtos = getTransactionsFromFile();
        log.info("<= sending {}", dtos);
        kafkaTemplate.send("rbc-transactions", dtos);
    }

    private List<TransactionDTO> getTransactionsFromFile() {
        try {
            /**
             * ObjectMapper objectMapper = new ObjectMapper();
             * TypeFactory typeFactory = objectMapper.getTypeFactory();
             * List<SomeClass> someClassList = objectMapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, SomeClass.class));
             */
            JsonParser parser = new JsonFactory().createParser(new File("src/main/resources/transactions.json"));
            return objectMapper.readValue(parser, objectMapper.getTypeFactory().constructCollectionType(List.class, TransactionDTO.class));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Read string from file failed.");
        }
    }
}
