package com.example.camunda.delegate;

import com.example.camunda.catalogue.TestProcessVariable;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Slf4j
public class FinishedDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Object skipWait = execution.getVariable(TestProcessVariable.WAIT.variableName);
        Object message = execution.getVariable(TestProcessVariable.CALL_BACK_MESSAGE.variableName);
        log.info("[DELEGATE] Process is finishing skipWait: {}, message: {}", skipWait, message);
    }
}