package com.project.petstore.controllers;

import com.project.petstore.dto.Response;
import com.project.petstore.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
@AllArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/get-all")
    public ResponseEntity<Response> listAllHistoryLogs() {
        Response response = historyService.listHistoryLogs();
        return ResponseEntity.status(response.getStatusCode())
                .body(response);
    }

}
