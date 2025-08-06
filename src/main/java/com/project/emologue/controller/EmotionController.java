package com.project.emologue.controller;

import com.project.emologue.model.emotion.Emotion;
import com.project.emologue.model.emotion.EmotionPatchRequestBody;
import com.project.emologue.model.emotion.EmotionPostRequestBody;
import com.project.emologue.model.error.ErrorResponse;
import com.project.emologue.model.job.Job;
import com.project.emologue.service.EmotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Emotion API", description = "감정 API")
@RestController
@RequestMapping("/api/v1/emotions")
public class EmotionController {

    @Autowired private EmotionService emotionService;

    @GetMapping("/user")
    @Operation(summary = "감정 조회", description = "감정 다건 조회")
    @ApiResponse(
            responseCode = "200", description = "감정 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Emotion.class),
                    examples = @ExampleObject(value = "[\n" +
                            " {\"emotionId\":\"1\", \"name\":\"짜증\", \"emotionType\":\"NEGATIVE\", \"description\": \"짜증\"}, \n" +
                            " {\"emotionId\":\"2\", \"name\":\"분노\", \"emotionType\":\"NEGATIVE\", \"description\": \"분노\"}\n" +
                            "]")
            )
    )
    public ResponseEntity<List<Emotion>> getEmotions() {
        List<Emotion> emotions = emotionService.getEmotions();
        return ResponseEntity.ok(emotions);
    }

    @GetMapping("/admin/{emotionId}")
    @Operation(summary = "감정 조회", description = "감정 단건 조회")
    @ApiResponse(
            responseCode = "200", description = "감정 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Job.class),
                    examples = @ExampleObject(value = "{\"emotionId\":\"11\", \"name\":\"행복\", \"emotionType\":\"POSITIVE\", \"description\": \"행복\"}")
            )
    )
    public ResponseEntity<Emotion> getEmotionByEmotionId(
            @Parameter(description = "조회할 emotionId", required = true, example = "11")
            @PathVariable long emotionId) {
        Emotion emotion = emotionService.getEmotionByEmotionId(emotionId);
        return ResponseEntity.ok(emotion);
    }

    @PostMapping("/admin")
    @Operation(summary = "감정 등록", description = "관리자 감정 신규 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "감정 등록 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmotionPostRequestBody.class),
                            examples = @ExampleObject(value = "{\"emotionId\":\"23\", \"name\":\"활기찬\", \"emotionType\":\"POSITIVE\", \"description\": \"활기찬\"}")
                    )),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"BAD_REQUEST\", \"message\":\"Required request body is missing\"}")
                    )),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"CONFLICT\", \"message\":\"Emotion with name 행복 already exists\"}")
                    ))
    })
    public ResponseEntity<Emotion> createEmotion(
            @Parameter(description = "감정 등록", required = true)
            @Valid @RequestBody EmotionPostRequestBody createEmotion) {
        Emotion emotion = emotionService.createEmotion(createEmotion);
        return ResponseEntity.ok(emotion);
    }

    @PatchMapping("/admin/{emotionId}")
    @Operation(summary = "감정 수정", description = "관리자 감정 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "감정 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmotionPatchRequestBody.class),
                            examples = @ExampleObject(value = "{\"emotionId\":\"11\", \"name\":\"행복과 감격\", \"emotionType\":\"POSITIVE\", \"description\": \"행복과 감격\"}")
                    )),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"BAD_REQUEST\", \"message\":\"Required request body is missing\"}")
                    ))
    })
    public ResponseEntity<Emotion> updateEmotion(
            @Parameter(description = "조회할 emotionId", required = true, example = "11")
            @PathVariable long emotionId,
            @Parameter(description = "수정할 내용", required = true)
            @Valid @RequestBody EmotionPatchRequestBody updateEmotion) {
        Emotion emotion = emotionService.updateEmotion(emotionId, updateEmotion);
        return ResponseEntity.ok(emotion);
    }

    @DeleteMapping("/admin/{emotionId}")
    @Operation(summary = "감정 삭제", description = "관리자 감정 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "감정 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"NOT_FOUND\", \"message\":\"Emotion with emotionId 100 not found\"}")
                    ))
    })
    public ResponseEntity<Void> deleteEmotion(
            @Parameter(description = "삭제할 emotionId", required = true, example = "100")
            @PathVariable long emotionId) {
        emotionService.deleteEmotion(emotionId);
        return ResponseEntity.noContent().build();
    }
}
