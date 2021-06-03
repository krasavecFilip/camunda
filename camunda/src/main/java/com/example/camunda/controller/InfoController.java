package com.example.camunda.controller;

import com.example.camunda.model.ErrorResponse;
import com.example.camunda.model.IdResponse;
import com.example.camunda.model.ProcessInfoResponse;
import com.example.camunda.service.HtmlService;
import com.example.camunda.service.InfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/info")
@Slf4j
public class InfoController {

    @Autowired
    private InfoService infoService;

    @Autowired
    private HtmlService htmlService;

    @Operation(summary = "Html for info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request received."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Camunda error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public String messageHtml(@Parameter(description = "ID of the process for info.", required = true)
                              @RequestParam String processId) {
        log.info("[REST] Creating html for info.");
        ProcessInfoResponse processInfoResponse = infoService.retrieveProcessInfo(processId);
        return htmlService.createInfoHtml(processInfoResponse);
    }

    @Operation(summary = "Retrieves all active process instances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request received.")
    })
    @GetMapping(path = "allActive", produces = "application/json")
    public List<IdResponse> retrieveAllActiveProcessIds() {
        log.info("[REST] Retrieving all active process instances:");
        List<String> allActiveProcessIds = infoService.retrieveAllActiveProcessIds();
        log.info("[REST] All active process instance ids: {}", allActiveProcessIds);
        return allActiveProcessIds.stream()
                .map(IdResponse::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Retrieves process info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request received."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Camunda error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(path = "process", produces = "application/json")
    public ProcessInfoResponse retrieveProcessInfo(@Parameter(description = "ID of the process for info.", required = true)
                                                   @RequestParam String processId) {
        log.info("[REST] Retrieving process info:");
        ProcessInfoResponse processInfoResponse = infoService.retrieveProcessInfo(processId);
        log.info("[REST] Process info: {}", processInfoResponse);
        return processInfoResponse;
    }
}