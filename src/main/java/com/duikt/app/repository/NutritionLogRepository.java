package com.duikt.app.repository;

import com.duikt.app.entity.NutritionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NutritionLogRepository extends JpaRepository<NutritionLogEntity, UUID> {
}
