package com.project.petstore.repository;

import com.project.petstore.models.HistoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryRecord, Long> {
}
