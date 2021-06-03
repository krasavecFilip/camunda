package com.example.camunda.service;

import com.example.camunda.model.ProcessInfoResponse;
import com.example.camunda.util.StringTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

@Service
@Slf4j
public class HtmlService {
    public String createStartHtml() {
        log.info("[SERVICE] creating start html");
        String html = "";
        try {
            Properties properties = new Properties();
            properties.put("item:title", "Start process");
            properties.put("resource:js", StringTemplate.resourceFileAsString("/template/main.js"));
            properties.put("resource:css", StringTemplate.resourceFileAsString("/template/main.css"));
            html = StringTemplate.replace("/template/index.html", properties);
        } catch (IOException e) {
            log.error("[SERVICE] Failed to create html");
        }
        return html;
    }

    public String createMessageHtml(String processId) {
        log.info("[SERVICE] creating message html");
        String html = "";
        try {
            Properties properties = new Properties();
            properties.put("item:title", "Message process");
            properties.put("item:processId", processId);
            properties.put("resource:js", StringTemplate.resourceFileAsString("/template/main.js"));
            properties.put("resource:css", StringTemplate.resourceFileAsString("/template/main.css"));
            html = StringTemplate.replace("/template/message.html", properties);
        } catch (IOException e) {
            log.error("[SERVICE] Failed to create html");
        }
        return html;
    }

    public String createInfoHtml(ProcessInfoResponse processInfoResponse) {
        log.info("[SERVICE] creating info html");
        String html = "";
        try {
            Properties properties = new Properties();
            properties.put("item:title", "Info process");
            properties.put("item:processId", processInfoResponse.getId());
            properties.put("item:status", processInfoResponse.getStatus());
            properties.put("item:wait", processInfoResponse.getWait());
            String message = processInfoResponse.getMessage();
            properties.put("item:message", message != null ? message : "N/A");
            properties.put("item:startTime", processInfoResponse.getStartTime());
            properties.put("item:overallTime", processInfoResponse.getOverallTime());
            properties.put("resource:js", StringTemplate.resourceFileAsString("/template/main.js"));
            properties.put("resource:css", StringTemplate.resourceFileAsString("/template/main.css"));
            html = StringTemplate.replace("/template/info.html", properties);
        } catch (IOException e) {
            log.error("[SERVICE] Failed to create html");
        }
        return html;
    }
}