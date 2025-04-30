package com.project.petstore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "history_records")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HistoryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_executed", nullable = false)
    private LocalDate dateExecuted;

    @Column(name = "success_count", nullable = false)
    private int successCount;

    @Column(name = "fail_count", nullable = false)
    private int failCount;

    public HistoryRecord(LocalDate dateExecuted, int successCount, int failCount) {
        this.dateExecuted = dateExecuted;
        this.successCount = successCount;
        this.failCount = failCount;
    }
}
