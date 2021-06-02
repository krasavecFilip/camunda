package com.example.camunda.service;

import com.example.camunda.catalogue.CallbackMessage;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContinueService {

    @Autowired
    private RuntimeService runtimeService;

    public String continueProcess(String processId, CallbackMessage callbackMessage) {
        log.info("[SERVICE] Gonna continue Camunda process with id: {}", processId);
        MessageCorrelationResult result = runtimeService
                .createMessageCorrelation(callbackMessage.message)
                .processInstanceId(processId)
                .correlateWithResult();
        String resultType = result.getResultType().toString();
        log.info("[SERVICE] ResultType: {}", resultType);
        return resultType;
    }
}