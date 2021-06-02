package com.example.camunda.service;

import com.example.camunda.catalogue.ProcessDefinitionName;
import com.example.camunda.catalogue.TestProcessVariable;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StartService {

    @Autowired
    private RuntimeService runtimeService;

    public String startProcess(boolean skipWait) {
        log.info("[SERVICE] Starting new process with name: {}", ProcessDefinitionName.TEST_PROCESS.processDefinition);
        Map<String, Object> params = new HashMap<>();
        params.put(TestProcessVariable.SKIP_WAIT.variableName, skipWait);

        ProcessInstance pi = runtimeService.startProcessInstanceByKey(ProcessDefinitionName.TEST_PROCESS.processDefinition, params);
        String processInstanceId = pi.getProcessInstanceId();
        log.info("[SERVICE] Process started id: {}", processInstanceId);
        return processInstanceId;
    }
}