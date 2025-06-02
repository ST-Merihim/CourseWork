package com.duikt.app.service;

import com.duikt.app.domain.WorkoutDTO;

import java.util.List;
import java.util.UUID;

public interface WorkoutService {
    List<WorkoutDTO> findAllWorkouts();
    WorkoutDTO findWorkoutById(UUID id);
    WorkoutDTO createWorkout(WorkoutDTO workoutDTO, UUID userId);
    WorkoutDTO updateWorkout(UUID id, WorkoutDTO workoutDTO);
    void deleteWorkout(UUID id);
}
