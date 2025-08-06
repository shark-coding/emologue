package com.project.emologue.controller;

import com.project.emologue.model.dto.EmotionStatisticsDto;
import com.project.emologue.service.EmotionStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Emotion Statistics API", description = "감정 통계 API")
@RestController
@RequestMapping("/api/v1/statistics")
public class EmotionStatisticsController {

    private final Logger logger = LoggerFactory.getLogger(EmotionStatisticsController.class);

    @Autowired
    private EmotionStatisticsService emotionStatisticsService;

    @GetMapping("/user/weekly/{userId}")
    @Operation(summary = "주단위 감정 통계", description = "주단위 감정 통계")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "주단위 감정 통계 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmotionStatisticsDto.class),
                            examples = @ExampleObject(value = "{\"emotionType\":\"POSITIVE\", \"count\":\"1\", \"ratio\":\"100.0\", \"job\": \"null\"}")
                            )
                    )
    })
    public ResponseEntity<List<EmotionStatisticsDto>> getWeeklyStatistics(
            @Parameter(description = "조회할 사용자 userId", required = true, example = "1")
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(emotionStatisticsService.getWeeklyStatistics(userId));
    }

    @GetMapping("/user/monthly/{userId}")
    @Operation(summary = "월단위 감정 통계", description = "월단위 감정 통계")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "월단위 감정 통계 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmotionStatisticsDto.class),
                            examples = @ExampleObject(value = "[\n" +
                                    " {\"emotionType\":\"POSITIVE\", \"count\":\"1\", \"ratio\":\"20.0\", \"job\": \"null\"}, \n" +
                                    " {\"emotionType\":\"NEGATIVE\", \"count\":\"4\", \"ratio\":\"80.0\", \"job\": \"null\"}\n" +
                                    "]")
                    )
            )
    })
    public ResponseEntity<List<EmotionStatisticsDto>> getMonthlyStatistics(
            @Parameter(description = "조회할 사용자 userId", required = true, example = "1")
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(emotionStatisticsService.getMonthlyStatistics(userId));
    }

    @GetMapping("/admin/jobs")
    @Operation(summary = "직업별 감정 통계", description = "직업별 감정 통계")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "직업별 감정 통계 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmotionStatisticsDto.class),
                            examples = @ExampleObject(value = "[\n" +
                                    " {\"emotionType\":\"POSITIVE\", \"count\":\"2\", \"ratio\":\"33.33\", \"job\": \"backend\"}, \n" +
                                    " {\"emotionType\":\"NEGATIVE\", \"count\":\"4\", \"ratio\":\"66.67\", \"job\": \"backend\"}\n" +
                                    "]")
                    )
            ),
            @ApiResponse(
                    responseCode = "403", description = "Forbidden", content = @Content()
            )
    })
    public ResponseEntity<List<EmotionStatisticsDto>> getJobsStatistics() {
        return ResponseEntity.ok(emotionStatisticsService.getJobsStatistics());
    }
}
