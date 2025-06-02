package com.duikt.app.API;

import com.duikt.app.domain.enums.Type;
import com.duikt.app.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@Tag(name = "Statistics", description = "API for retrieving workout and progress statistics")
public class StatsRestController {

    private final StatsService statsService;

    @GetMapping("/workouts/by-type")
    @Operation(summary = "Retrieve workout statistics by type", description = "Returns workout statistics grouped by type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout statistics retrieved successfully")
    })
    public ResponseEntity<Map<Type, Map<String, Integer>>> getWorkoutStatsByType() {
        Map<Type, Map<String, Integer>> stats = statsService.getWorkoutStatsByType();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/progress/calories")
    @Operation(summary = "Retrieve calorie progress over time", description = "Returns a map of dates and corresponding calorie progress.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calorie progress retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date format provided")
    })
    public ResponseEntity<Map<LocalDate, Double>> getCaloriesProgress(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        Map<LocalDate, Double> progressStats = statsService.getCaloriesProgress(from, to);
        return ResponseEntity.ok(progressStats);
    }
}
