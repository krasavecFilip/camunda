package com.example.camunda.catalogue;

public enum ProcessDefinitionName {
    TEST_PROCESS("TestProcess");

    public final String processDefinition;

    private ProcessDefinitionName(String processDefinition) {
        this.processDefinition = processDefinition;
    }
}