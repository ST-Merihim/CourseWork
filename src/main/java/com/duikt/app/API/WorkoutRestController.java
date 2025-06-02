package com.duikt.app.API;

import com.duikt.app.domain.WorkoutDTO;
import com.duikt.app.service.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
@Tag(name = "Workouts", description = "API for managing workout sessions")
public class WorkoutRestController {

    private final WorkoutService workoutService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Retrieve all workouts", description = "Returns a list of all registered workouts for authorized users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workouts retrieved successfully")
    })
    public ResponseEntity<List<WorkoutDTO>> getWorkouts() {
        return ResponseEntity.ok(workoutService.findAllWorkouts());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Retrieve a workout by ID", description = "Returns details of a specific workout based on UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Workout not found")
    })
    public ResponseEntity<WorkoutDTO> getWorkout(@PathVariable UUID id) {
        return ResponseEntity.ok(workoutService.findWorkoutById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new workout", description = "Creates a new workout session based on input data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Workout created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<WorkoutDTO> createWorkout(@Valid @RequestBody WorkoutDTO workoutDTO, @RequestParam UUID userId) {
        return new ResponseEntity<>(workoutService.createWorkout(workoutDTO, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update a workout", description = "Modifies an existing workout session with new data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout updated successfully"),
            @ApiResponse(responseCode = "404", description = "Workout not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<WorkoutDTO> updateWorkout(@PathVariable UUID id, @Valid @RequestBody WorkoutDTO workoutDTO) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, workoutDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a workout", description = "Deletes a workout session based on its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Workout deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Workout not found")
    })
    public void deleteWorkout(@PathVariable UUID id) {
        workoutService.deleteWorkout(id);
    }
}
