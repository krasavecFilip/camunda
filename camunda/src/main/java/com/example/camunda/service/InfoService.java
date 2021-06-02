package com.example.camunda.service;

import com.example.camunda.catalogue.ProcessDefinitionName;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InfoService {

    public List<String> retrieveAllActiveProcessIds() {
        log.info("[SERVICE] Retrieving all active process ids.");
        ProcessEngine processEngine = BpmPlatform.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessDefinition myProcessDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionName(ProcessDefinitionName.TEST_PROCESS.processDefinition)
                .latestVersion()
                .singleResult();

        List<ProcessInstance> processInstances = runtimeService
                .createProcessInstanceQuery()
                .processDefinitionId(myProcessDefinition.getId())
                .active()
                .list();
        List<String> allActiveProcessIds = processInstances.stream()
                .map(Execution::getId)
                .collect(Collectors.toList());
        log.info("[SERVICE] All active process ids: {}", allActiveProcessIds);
        return allActiveProcessIds;
    }
}