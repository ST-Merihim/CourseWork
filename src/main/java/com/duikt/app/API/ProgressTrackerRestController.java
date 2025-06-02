package com.duikt.app.API;

import com.duikt.app.domain.ProgressTrackerDTO;
import com.duikt.app.service.ProgressTrackerService;
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
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
@Tag(name = "Progress Tracker", description = "API for managing progress tracking")
public class ProgressTrackerRestController {

    private final ProgressTrackerService progressTrackerService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Retrieve all progress entries", description = "Returns a list of all progress tracking entries for authorized users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Progress entries retrieved successfully")
    })
    public ResponseEntity<List<ProgressTrackerDTO>> getProgressEntries() {
        return ResponseEntity.ok(progressTrackerService.findAllProgressEntries());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Retrieve a progress entry by ID", description = "Returns a specific progress tracking entry based on UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Progress entry retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Progress entry not found")
    })
    public ResponseEntity<ProgressTrackerDTO> getProgressEntry(@PathVariable UUID id) {
        return ResponseEntity.ok(progressTrackerService.findProgressEntryById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new progress entry", description = "Creates a new progress tracking entry based on input data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Progress entry created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ProgressTrackerDTO> createProgressEntry(@Valid @RequestBody ProgressTrackerDTO progressTrackerDTO,
                                                                  @RequestParam UUID userId) {
        return new ResponseEntity<>(progressTrackerService.createProgressEntry(progressTrackerDTO, userId),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update a progress entry", description = "Updates an existing progress tracking entry with new data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Progress entry updated successfully"),
            @ApiResponse(responseCode = "404", description = "Progress entry not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ProgressTrackerDTO> updateProgressEntry(@PathVariable UUID id,
                                                                  @Valid @RequestBody ProgressTrackerDTO progressTrackerDTO) {
        return ResponseEntity.ok(progressTrackerService.updateProgressEntry(id, progressTrackerDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a progress entry", description = "Deletes a progress tracking entry by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Progress entry deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Progress entry not found")
    })
    public void deleteProgressEntry(@PathVariable UUID id) {
        progressTrackerService.deleteProgressEntry(id);
    }
}
