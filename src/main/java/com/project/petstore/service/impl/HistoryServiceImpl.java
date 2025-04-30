package com.project.petstore.service.impl;

import com.project.petstore.dto.HistoryRecordDTO;
import com.project.petstore.dto.Response;
import com.project.petstore.exceptions.CustomException;
import com.project.petstore.models.HistoryRecord;
import com.project.petstore.repository.HistoryRepository;
import com.project.petstore.service.HistoryService;
import com.project.petstore.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;


    @Override
    public Response listHistoryLogs() {
        Response response = new Response();

        try {
            List<HistoryRecord> historyRecords = historyRepository.findAll();

            List<HistoryRecordDTO> historyRecordDTOS = Utils.historyRecordsToHistoryRecordsDTOS(historyRecords);

            response.setHistoryRecordDTOS(historyRecordDTOS);
            response.setMessage("Successful");
            response.setStatusCode(200);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error listing history logs: " + e.getMessage());
        }
        return response;
    }
}
