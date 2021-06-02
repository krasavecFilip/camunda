package com.example.camunda.controller;

import com.example.camunda.service.InfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/info")
@Slf4j
public class InfoController {

    @Autowired
    private InfoService infoService;

    @Operation(summary = "Retrieves all active process instances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request received.")
    })
    @GetMapping()
    public List<String> retrieveAllActiveProcessIds() {
        log.info("[REST] Retrieving all active process instances:");
        List<String> allActiveProcessIds = infoService.retrieveAllActiveProcessIds();
        log.info("[REST] All active process instance ids: {}", allActiveProcessIds);
        return allActiveProcessIds;
    }
}