package com.example.camunda.catalogue;

public enum TestProcessVariable {
    WAIT("wait"),
    CALL_BACK_MESSAGE("callBackMessage");

    public final String variableName;

    private TestProcessVariable(String variableName) {
        this.variableName = variableName;
    }
}