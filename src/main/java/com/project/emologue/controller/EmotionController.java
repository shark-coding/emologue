package com.project.emologue.controller;

import com.project.emologue.model.emotion.Emotion;
import com.project.emologue.model.emotion.EmotionRequestBody;
import com.project.emologue.service.EmotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emotions")
public class EmotionController {

    @Autowired private EmotionService emotionService;

    @GetMapping
    public ResponseEntity<List<Emotion>> getEmotions() {
        List<Emotion> emotions = emotionService.getEmotions();
        return ResponseEntity.ok(emotions);
    }

    @GetMapping("/{emotionId}")
    public ResponseEntity<Emotion> getEmotionByEmotionId(
            @PathVariable long emotionId) {
        Emotion emotion = emotionService.getEmotionByEmotionId(emotionId);
        return ResponseEntity.ok(emotion);
    }

    @PostMapping
    public ResponseEntity<Emotion> createEmotion(
            @Valid @RequestBody EmotionRequestBody createEmotion) {
        Emotion emotion = emotionService.createEmotion(createEmotion);
        return ResponseEntity.ok(emotion);
    }

    @PatchMapping("/{emotionId}")
    public ResponseEntity<Emotion> updateEmotion(
            @PathVariable long emotionId,
            @Valid @RequestBody EmotionRequestBody updateEmotion) {
        Emotion emotion = emotionService.updateEmotion(emotionId, updateEmotion);
        return ResponseEntity.ok(emotion);
    }

    @DeleteMapping("/{emotionId}")
    public ResponseEntity<Void> deleteEmotion(
            @PathVariable long emotionId) {
        emotionService.deleteEmotion(emotionId);
        return ResponseEntity.noContent().build();
    }
}
