package com.example.rbc_kafka_tester.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractDto implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;
}
