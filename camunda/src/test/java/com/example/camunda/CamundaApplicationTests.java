package com.example.camunda;

import com.example.camunda.catalogue.ProcessDefinitionName;
import com.example.camunda.service.InfoService;
import com.example.camunda.service.StartService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CamundaApplicationTests {

    public static final String COMPLETED = "COMPLETED";
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private StartService startService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private InfoService infoService;

    @Test
    void happyPathSkipTrueTest() {
        String id = startService.startProcess(true);
        List<HistoricProcessInstance> testProcesses = historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionKey(ProcessDefinitionName.TEST_PROCESS.processDefinition)
                .list();
        assertThat(testProcesses.size()).isEqualTo(1);
        HistoricProcessInstance historicProcessInstance = testProcesses.get(0);
        assertThat(historicProcessInstance.getState()).isEqualTo(COMPLETED);
        assertThat(historicProcessInstance.getId()).isEqualTo(id);
    }
}