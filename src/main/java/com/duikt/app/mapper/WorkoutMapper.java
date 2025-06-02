package com.duikt.app.mapper;

import com.duikt.app.domain.WorkoutDTO;
import com.duikt.app.entity.WorkoutEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkoutMapper {

    public WorkoutDTO toWorkoutDto(WorkoutEntity entity) {
        return WorkoutDTO.builder()
                .id(entity.getId())
                .type(entity.getType())
                .date(entity.getDate())
                .duration(entity.getDuration())
                .caloriesBurned(entity.getCaloriesBurned())
                .build();
    }

    public WorkoutEntity toWorkoutEntity(WorkoutDTO dto) {
        return WorkoutEntity.builder()
                .id(dto.getId())
                .type(dto.getType())
                .date(dto.getDate())
                .duration(dto.getDuration())
                .caloriesBurned(dto.getCaloriesBurned())
                .build();
    }

    public List<WorkoutDTO> toWorkoutDtoList(List<WorkoutEntity> entities) {
        return entities.stream()
                .map(this::toWorkoutDto)
                .collect(Collectors.toList());
    }
}
