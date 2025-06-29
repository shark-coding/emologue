package com.project.emologue.controller;

import com.project.emologue.model.diary.Diary;
import com.project.emologue.model.diary.DiaryEntityPatchRequestBody;
import com.project.emologue.model.diary.DiaryEntityPostRequestBody;
import com.project.emologue.model.entity.AdminEntity;
import com.project.emologue.model.entity.UserEntity;
import com.project.emologue.repository.DiaryEntityRepository;
import com.project.emologue.service.DiaryService;
import com.project.emologue.service.QuestionAnswerService;
import com.project.emologue.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryController {

    @Autowired private DiaryService diaryService;
    @Autowired private UserService userService;
    @Autowired
    private DiaryEntityRepository diaryEntityRepository;

    @GetMapping("/admin")
    public ResponseEntity<List<Diary>> getAllDiaries() {
        List<Diary> diaries = diaryService.getAllDiaries();
        return ResponseEntity.ok(diaries);
    }

    @GetMapping
    public ResponseEntity<List<Diary>> getMyDiaries() {
        UserEntity user = userService.getCurrentUser();
        return ResponseEntity.ok(diaryService.getDiariesByUser(user));
    }

    @PostMapping
    public ResponseEntity<Diary> createDiary(
            @Valid @RequestBody DiaryEntityPostRequestBody diaryEntityPostRequestBody) {
        diaryEntityPostRequestBody.validate();
        var user = userService.getCurrentUser();
        Diary diary = diaryService.createDiary(user, diaryEntityPostRequestBody);
        return ResponseEntity.ok(diary);
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<Diary> updateDiary(
            @PathVariable Long diaryId,
            @Valid @RequestBody DiaryEntityPatchRequestBody diaryEntityPatchRequestBody) {
        diaryEntityPatchRequestBody.validate();
        UserEntity user = userService.getCurrentUser();
        Diary diary = diaryService.updateDiary(diaryId, user, diaryEntityPatchRequestBody);
        return ResponseEntity.ok(diary);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long diaryId) {
        UserDetails userDetails = userService.getCurrentUserAndAdmin();

        if (userDetails instanceof UserEntity user) {
            diaryService.deleteDiary(diaryId, user);
        } else if (userDetails instanceof AdminEntity admin) {
            diaryService.deleteDiaryAdmin(diaryId,admin);
        }
        return ResponseEntity.noContent().build();
    }

}
