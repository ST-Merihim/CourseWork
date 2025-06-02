package com.duikt.app.API;

import com.duikt.app.domain.NutritionLogDTO;
import com.duikt.app.service.NutritionLogService;
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
@RequestMapping("/api/v1/nutrition")
@RequiredArgsConstructor
@Tag(name = "Nutrition Logs", description = "API for managing nutrition logs")
public class NutritionLogRestController {

    private final NutritionLogService nutritionLogService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Retrieve all nutrition logs", description = "Returns a list of all available nutrition logs for authorized users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nutrition logs retrieved successfully")
    })
    public ResponseEntity<List<NutritionLogDTO>> getNutritionLogs() {
        return ResponseEntity.ok(nutritionLogService.findAllLogs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Retrieve a nutrition log by ID", description = "Returns a specific nutrition log based on UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nutrition log retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Nutrition log not found")
    })
    public ResponseEntity<NutritionLogDTO> getNutritionLog(@PathVariable UUID id) {
        return ResponseEntity.ok(nutritionLogService.findLogById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new nutrition log", description = "Creates a new nutrition log based on input data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nutrition log created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<NutritionLogDTO> createNutritionLog(@Valid @RequestBody NutritionLogDTO nutritionLogDTO,
                                                              @RequestParam UUID userId) {
        return new ResponseEntity<>(nutritionLogService.createLog(nutritionLogDTO, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update a nutrition log", description = "Updates an existing nutrition log with new data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nutrition log updated successfully"),
            @ApiResponse(responseCode = "404", description = "Nutrition log not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<NutritionLogDTO> updateNutritionLog(@PathVariable UUID id,
                                                              @Valid @RequestBody NutritionLogDTO nutritionLogDTO) {
        return ResponseEntity.ok(nutritionLogService.updateLog(id, nutritionLogDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a nutrition log", description = "Deletes a nutrition log by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nutrition log deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Nutrition log not found")
    })
    public void deleteNutritionLog(@PathVariable UUID id) {
        nutritionLogService.deleteLog(id);
    }
}
