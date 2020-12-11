package com.example.rbc_kafka_tester.controller;

import com.example.rbc_kafka_tester.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KafkaSendController {

    private final TransactionService transactionService;

    @Autowired
    public KafkaSendController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/send")
    public String send() {
        transactionService.produce();
        return "sendedPage";
    }
}
