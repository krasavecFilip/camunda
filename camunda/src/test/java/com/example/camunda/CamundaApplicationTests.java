package com.example.camunda;

import com.example.camunda.catalogue.TestProcessVariable;
import com.example.camunda.service.ContinueService;
import com.example.camunda.service.InfoService;
import com.example.camunda.service.StartService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CamundaApplicationTests {

    public static final String COMPLETED = "COMPLETED";
    public static final String ACTIVE = "ACTIVE";

    @Autowired
    private StartService startService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private ContinueService continueService;

    @Test
    void happyPathSkipTrueTest() {
        String id = startService.startProcess(false);

        HistoricProcessInstance historicProcessInstance = retrieveProcessInstance(id);
        assertThat(historicProcessInstance.getState()).isEqualTo(COMPLETED);
        assertThat(historicProcessInstance.getId()).isEqualTo(id);
    }

    @Test
    void happyPathSkipFalseTest() {
        String testMessage = "test message";
        String id = startService.startProcess(true);

        HistoricProcessInstance historicProcessInstance = retrieveProcessInstance(id);
        assertThat(historicProcessInstance.getState()).isEqualTo(ACTIVE);
        assertThat(historicProcessInstance.getId()).isEqualTo(id);

        List<String> activeIds = infoService.retrieveAllActiveProcessIds();
        assertThat(activeIds.size()).isEqualTo(1);
        assertThat(activeIds.get(0)).isEqualTo(id);

        continueService.continueProcess(id, testMessage);

        HistoricProcessInstance historicProcessInstanceUpdated = retrieveProcessInstance(id);
        assertThat(historicProcessInstanceUpdated.getState()).isEqualTo(COMPLETED);
        assertThat(historicProcessInstanceUpdated.getId()).isEqualTo(id);

        Object message = retrieveVariableValue(id, TestProcessVariable.CALL_BACK_MESSAGE.variableName);
        assertThat(message).isEqualTo(testMessage);

        List<String> activeIdsAfterFinish = infoService.retrieveAllActiveProcessIds();
        assertThat(activeIdsAfterFinish.size()).isEqualTo(0);
    }

    private HistoricProcessInstance retrieveProcessInstance(String id) {
        List<HistoricProcessInstance> testProcesses = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(id)
                .list();
        assertThat(testProcesses.size()).isEqualTo(1);
        return testProcesses.get(0);
    }

    private Object retrieveVariableValue(String id, String variableName) {
        List<HistoricVariableInstance> callBackMessage = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(id)
                .variableName(variableName)
                .list();
        assertThat(callBackMessage.size()).isEqualTo(1);
        return callBackMessage.get(0).getValue();
    }
}