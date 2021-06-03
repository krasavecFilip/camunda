package com.example.camunda.service;

import com.example.camunda.catalogue.ProcessDefinitionName;
import com.example.camunda.catalogue.TestProcessVariable;
import com.example.camunda.model.ProcessInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InfoService {

    @Autowired
    private HistoryService historyService;

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

    public ProcessInfoResponse retrieveProcessInfo(String processId) {
        log.info("[SERVICE] Retrieving process info.");
        ProcessInfoResponse processInfoResponse = new ProcessInfoResponse();
        processInfoResponse.setId(processId);
        try {
            HistoricProcessInstance historicProcessInstance = historyService
                    .createHistoricProcessInstanceQuery()
                    .processInstanceId(processId)
                    .list()
                    .get(0);
            processInfoResponse.setStartTime(historicProcessInstance.getStartTime().toString());
            processInfoResponse.setOverallTime(historicProcessInstance.getDurationInMillis().toString());
            processInfoResponse.setStatus(historicProcessInstance.getState());
            List<HistoricVariableInstance> historicVariableInstancesMessages = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(processId)
                    .variableName(TestProcessVariable.CALL_BACK_MESSAGE.variableName)
                    .list();
            if (historicVariableInstancesMessages.size() != 0) {
                processInfoResponse.setMessage(historicVariableInstancesMessages
                        .get(0)
                        .getValue()
                        .toString());
            }
            processInfoResponse.setWait(historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(processId)
                    .variableName(TestProcessVariable.WAIT.variableName)
                    .list()
                    .get(0)
                    .getValue()
                    .toString());
        } catch (Exception e) {
            throw new ProcessEngineException("Error while fetching process info", e);
        }
        return processInfoResponse;
    }
}