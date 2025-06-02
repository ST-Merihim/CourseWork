package com.duikt.app.API;

import com.duikt.app.domain.GoalDTO;
import com.duikt.app.service.GoalService;
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
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
@Tag(name = "Goals", description = "API for managing user goals")
public class GoalRestController {

    private final GoalService goalService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Retrieve all goals", description = "Returns a list of all available goals for authorized users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goals retrieved successfully")
    })
    public ResponseEntity<List<GoalDTO>> getGoals() {
        return ResponseEntity.ok(goalService.findAllGoals());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Retrieve a goal by ID", description = "Returns a specific user goal based on UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Goal not found")
    })
    public ResponseEntity<GoalDTO> getGoal(@PathVariable UUID id) {
        return ResponseEntity.ok(goalService.findGoalById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new goal", description = "Creates a new user goal based on input data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Goal created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<GoalDTO> createGoal(@Valid @RequestBody GoalDTO goalDTO, @RequestParam UUID userId) {
        return new ResponseEntity<>(goalService.createGoal(goalDTO, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update a goal", description = "Updates an existing goal with new data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal updated successfully"),
            @ApiResponse(responseCode = "404", description = "Goal not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<GoalDTO> updateGoal(@PathVariable UUID id, @Valid @RequestBody GoalDTO goalDTO) {
        return ResponseEntity.ok(goalService.updateGoal(id, goalDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a goal", description = "Deletes a goal by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Goal deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Goal not found")
    })
    public void deleteGoal(@PathVariable UUID id) {
        goalService.deleteGoal(id);
    }
}
