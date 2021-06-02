package com.example.camunda.controller;

import com.example.camunda.catalogue.ProcessDefinitionName;
import com.example.camunda.service.StartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/start")
@Slf4j
public class StartController {

    @Autowired
    private StartService startService;

    @Operation(summary = "Starting new process")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request received."),
            @ApiResponse(responseCode = "400", description = "Bad request.")})
    @GetMapping()
    public String start(@Parameter(description = "Value whether to skip the wait step", required = true) @RequestParam boolean skipWait) {
        log.info("[REST] Starting new process with name: " + ProcessDefinitionName.TEST_PROCESS.processDefinition);
        String id = startService.startProcess(skipWait);
        log.info("[SERVICE] Process started id: {}", id);
        return id;
    }
}