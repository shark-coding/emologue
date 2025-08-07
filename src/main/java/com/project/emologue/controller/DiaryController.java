package com.project.emologue.controller;

import com.project.emologue.model.diary.Diary;
import com.project.emologue.model.diary.DiaryEntityPatchRequestBody;
import com.project.emologue.model.diary.DiaryEntityPostRequestBody;
import com.project.emologue.model.entity.AdminEntity;
import com.project.emologue.model.entity.UserEntity;
import com.project.emologue.model.error.ErrorResponse;
import com.project.emologue.repository.DiaryEntityRepository;
import com.project.emologue.service.DiaryService;
import com.project.emologue.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Diary API", description = "다이어리 API")
@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryController {

    @Autowired private DiaryService diaryService;
    @Autowired private UserService userService;
    @Autowired private DiaryEntityRepository diaryEntityRepository;

    @GetMapping("/admin")
    @Operation(summary = "다이어리 전체 조회", description = "관리자 다이어리 조회")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "다이어리 전체 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Diary.class))
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content())
    })
    public ResponseEntity<List<Diary>> getAllDiaries(
            @Parameter(description = "다이어리 타입(FREE/QUESTION)", example = "null")
            @RequestParam(required = false) String type) {
        List<Diary> diaries = diaryService.getAllDiaries(type);
        return ResponseEntity.ok(diaries);
    }

    @GetMapping("/user")
    @Operation(summary = "다이어리 전체 조회", description = "사용자 다이어리 조회")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "다이어리 전체 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Diary.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"UNAUTHORIZED\", \"message\":\"JWT expired 63144356 milliseconds ago at 2025-08-05T12:14:47.000Z. Current time: 2025-08-06T05:47:11.356Z. Allowed clock skew: 0 milliseconds.\"}")
                    ))
    })
    public ResponseEntity<List<Diary>> getMyDiaries(
            @Parameter(description = "다이어리 타입(FREE/QUESTION)", example = "null")
            @RequestParam(required = false) String type) {
        UserEntity user = userService.getCurrentUser();
        return ResponseEntity.ok(diaryService.getDiariesByUser(user, type));
    }

    @PostMapping("/user")
    @Operation(summary = "다이어리 작성", description = "사용자 다이어리 작성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "다이어리 작성 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Diary.class),
                    examples = @ExampleObject(value = "{\n" +
                            "  \"type\": \"QUESTION\",\n" +
                            "  \"questionAnswer\": {\n" +
                            "    \"firstAnswer\": 8,\n" +
                            "    \"secondAnswer\": \"주짓수를 하는데 앞구르기가 잘 안됐어. 이제 막 운동을 시작하는 건데, 뭔가 부끄럽고 창피한데 그래도 용기내서 했던거였어. 근데 잘 안되는데 괜히 아무도 나를 보고 뭐라고 하지 않았지만 혼자서 위축되는 하루였어.\",\n" +
                            "    \"thirdAnswer\": \"오늘 나는 나를 한층 더 건강하게 하루를 마무리할 수 있도록 운동을 했어.\",\n" +
                            "    \"fourthAnswer\":14\n" +
                            "  },\n" +
                            "  \"freeDiaryContent\": null\n" +
                            "}")
            )),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"UNAUTHORIZED\", \"message\":\"JWT expired 63144356 milliseconds ago at 2025-08-05T12:14:47.000Z. Current time: 2025-08-06T05:47:11.356Z. Allowed clock skew: 0 milliseconds.\"}")
                    )),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"CONFLICT\", \"message\":\"이미 오늘 일기를 작성했습니다.\"}")
                    ))
    })
    public ResponseEntity<Diary> createDiary(
            @Parameter(description = "다이어리 작성", required = true)
            @Valid @RequestBody DiaryEntityPostRequestBody diaryEntityPostRequestBody) {
        diaryEntityPostRequestBody.validate();
        var user = userService.getCurrentUser();
        Diary diary = diaryService.createDiary(user, diaryEntityPostRequestBody);
        return ResponseEntity.ok(diary);
    }

    @PatchMapping("/user/{diaryId}")
    @Operation(summary = "다이어리 수정", description = "사용자 다이어리 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "다이어리 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Diary.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"type\": \"QUESTION\",\n" +
                                    "  \"questionAnswer\": {\n" +
                                    "    \"firstAnswer\": 11,\n" +
                                    "    \"secondAnswer\": \"오늘 하루는 어땠으려나,\",\n" +
                                    "    \"thirdAnswer\": \"말하다 보니 기분이 좋아져\",\n" +
                                    "    \"fourthAnswer\":21\n" +
                                    "  },\n" +
                                    "  \"freeDiaryContent\": null\n" +
                                    "}")
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"UNAUTHORIZED\", \"message\":\"JWT expired 63144356 milliseconds ago at 2025-08-05T12:14:47.000Z. Current time: 2025-08-06T05:47:11.356Z. Allowed clock skew: 0 milliseconds.\"}")
                    )),
            @ApiResponse(responseCode = "406", description = "Not Acceptable",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"NOT_ACCEPTABLE\", \"message\":\"해당 user의 다이어리를 찾을 수 없습니다.\"}")
                    ))
    })
    public ResponseEntity<Diary> updateDiary(
            @Parameter(description = "수정할 diaryId", example = "1")
            @PathVariable Long diaryId,
            @Parameter(description = "수정할 내용")
            @Valid @RequestBody DiaryEntityPatchRequestBody diaryEntityPatchRequestBody) {
        diaryEntityPatchRequestBody.validate();
        UserEntity user = userService.getCurrentUser();
        Diary diary = diaryService.updateDiary(diaryId, user, diaryEntityPatchRequestBody);
        return ResponseEntity.ok(diary);
    }

    @DeleteMapping("/user/{diaryId}")
    @Operation(summary = "다이어리 삭제", description = "사용자 다이어리 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "다이어리 삭제 성공"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"NOT_ACCEPTABLE\", \"message\":\"해당 user의 다이어리를 찾을 수 없습니다.\"}")
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"UNAUTHORIZED\", \"message\":\"JWT expired 63144356 milliseconds ago at 2025-08-05T12:14:47.000Z. Current time: 2025-08-06T05:47:11.356Z. Allowed clock skew: 0 milliseconds.\"}")
                    ))
    })
    public ResponseEntity<Void> deleteDiary(
            @Parameter(description = "삭제할 diaryId", required = true, example = "100")
            @PathVariable Long diaryId) {
        UserDetails userDetails = userService.getCurrentUserAndAdmin();

        if (userDetails instanceof UserEntity user) {
            diaryService.deleteDiary(diaryId, user);
        } else if (userDetails instanceof AdminEntity admin) {
            diaryService.deleteDiaryAdmin(diaryId,admin);
        }
        return ResponseEntity.noContent().build();
    }

}
