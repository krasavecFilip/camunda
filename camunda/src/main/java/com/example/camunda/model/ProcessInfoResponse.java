package com.example.camunda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInfoResponse {
    private String id;
    private String status;
    private String wait;
    private String message;
    private String startTime;
    private String overallTime;
}