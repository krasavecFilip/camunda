package com.example.camunda.advice;

import com.example.camunda.model.ErrorResponse;
import org.camunda.bpm.engine.ProcessEngineException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProcessEngineException.class)
    public ResponseEntity<Object> handleProcessEngineException(RuntimeException ex) {
        JSONObject errorJson = new JSONObject(new ErrorResponse(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorJson.toString());
    }
}