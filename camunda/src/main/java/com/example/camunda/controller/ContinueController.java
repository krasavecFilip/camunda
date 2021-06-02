package com.example.camunda.controller;

import com.example.camunda.service.ContinueService;
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
@RequestMapping("/api/v1/message")
@Slf4j
public class ContinueController {

    @Autowired
    private ContinueService continueService;

    @Operation(summary = "Continue process with message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request received."),
            @ApiResponse(responseCode = "409", description = "Camunda error.")})
    @GetMapping()
    public void messageProcess(@Parameter(description = "ID of the process to be progressed.", required = true) @RequestParam String processId, @Parameter(description = "The message to be sent.", required = true) @RequestParam String message) {
        log.info("[REST] Gonna continue Camunda process with id: {}, message {}", processId, message);
        continueService.continueProcess(processId, message);
    }
}