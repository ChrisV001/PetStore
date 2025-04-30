package com.project.petstore.controllers.graphql;

import com.project.petstore.dto.HistoryRecordDTO;
import com.project.petstore.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HistoryControllerQL {

    private final HistoryService historyService;

    @QueryMapping
    public List<HistoryRecordDTO> listAllHistoryLogs() {
        return historyService.listHistoryLogs().getHistoryRecordDTOS();
    }
}
