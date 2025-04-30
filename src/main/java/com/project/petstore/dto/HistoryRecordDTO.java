package com.project.petstore.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoryRecordDTO {

    private LocalDate dateExecuted;

    private int successCount;

    private int failCount;

}
