package com.example.camunda.controller;

import com.example.camunda.model.ErrorResponse;
import com.example.camunda.model.MessageRequest;
import com.example.camunda.model.StatusResponse;
import com.example.camunda.service.HtmlService;
import com.example.camunda.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/message")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private HtmlService htmlService;

    @Operation(summary = "Html for message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request received."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public String messageHtml(@Parameter(description = "ID of the process to be progressed.", required = true)
                              @RequestParam String processId) {
        log.info("[REST] Creating html for message.");
        return htmlService.createMessageHtml(processId);
    }

    @Operation(summary = "Continue process with message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request received."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Camunda error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(produces = "application/json", consumes = "application/json")
    public StatusResponse messageProcess(@Parameter(description = "ID of the process to be progressed with message to be sent.", required = true)
                                         @RequestBody MessageRequest messageRequest) {
        String message = messageRequest.getMessage();
        String processId = messageRequest.getProcessId();
        log.info("[REST] Gonna continue Camunda process with id: {}, message {}", processId, message);
        messageService.continueProcess(processId, message);
        return new StatusResponse("success");
    }
}