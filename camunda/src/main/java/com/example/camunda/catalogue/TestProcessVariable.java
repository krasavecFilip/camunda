package com.example.camunda.catalogue;

public enum TestProcessVariable {
    SKIP_WAIT("skipWait");

    public final String variableName;

    private TestProcessVariable(String variableName) {
        this.variableName = variableName;
    }
}