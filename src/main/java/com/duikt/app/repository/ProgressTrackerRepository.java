package com.duikt.app.repository;

import com.duikt.app.entity.ProgressTrackerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProgressTrackerRepository extends JpaRepository<ProgressTrackerEntity, UUID> {
    List<ProgressTrackerEntity> findByDateBetween(LocalDate from, LocalDate to);
}
