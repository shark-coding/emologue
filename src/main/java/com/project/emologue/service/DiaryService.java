package com.project.emologue.service;

import com.project.emologue.exception.diary.DiaryAlreadyExistsException;
import com.project.emologue.exception.diary.DiaryNotFoundException;
import com.project.emologue.model.diary.Diary;
import com.project.emologue.model.diary.DiaryEntityPatchRequestBody;
import com.project.emologue.model.diary.DiaryEntityPostRequestBody;
import com.project.emologue.model.diary.DiaryType;
import com.project.emologue.model.entity.*;
import com.project.emologue.model.user.Role;
import com.project.emologue.repository.DiaryEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiaryService {

    @Autowired private DiaryEntityRepository diaryEntityRepository;
//    @Autowired private UserService userService;
//    @Autowired private EmotionService emotionService;
    @Autowired private QuestionAnswerService questionAnswerService;
    @Autowired private FreeDiaryContentService freeDiaryContentService;

    public List<Diary> getAllDiaries(String diaryType) {
        List<DiaryEntity> diaryEntities;
        if (diaryType == null) {
            diaryEntities = diaryEntityRepository.findAll();
        } else {
            DiaryType type = DiaryType.from(diaryType);

            diaryEntities = diaryEntityRepository.findAllByType(type);
        }
        return diaryEntities.stream().map(Diary::from).toList();
    }


    public List<Diary> getDiariesByUser(UserEntity user, String diaryType) {
        List<DiaryEntity> diaryEntities;
        if (diaryType == null) {
            diaryEntities = diaryEntityRepository.findAllByUser(user);
        } else {
            DiaryType type = DiaryType.from(diaryType);
            diaryEntities = diaryEntityRepository.findAllByUserAndType(user, type);
        }
        return diaryEntities.stream().map(Diary::from).toList();
    }

    public DiaryEntity getDiaryEntityByDiaryId(Long diaryId) {
        return diaryEntityRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("해당 user의 다이어리를 찾을 수 없습니다."));
    }


    @Transactional
    public Diary createDiary(UserEntity user, DiaryEntityPostRequestBody requestBody) {
        if (diaryEntityRepository.existsByUserAndCreatedDateTime(user, LocalDate.now())) {
            throw new DiaryAlreadyExistsException("이미 오늘 일기를 작성했습니다.");
        }
        DiaryEntity diaryEntity;
        if (requestBody.type() == DiaryType.QUESTION) {
            QuestionAnswerEntity answer =
                    questionAnswerService.createQuestionAnswer(requestBody);
            diaryEntity = DiaryEntity.ofQuestion(
                    user, requestBody.type(), answer);
        }
        else if (requestBody.type() == DiaryType.FREE) {
            FreeDiaryContentEntity content =
                    freeDiaryContentService.createFreeContent(requestBody);
            diaryEntity = DiaryEntity.ofFree(
                    user, requestBody.type(), content);
        }
        else {
            throw new DiaryNotFoundException();
        }
        return Diary.from(diaryEntityRepository.save(diaryEntity));
    }

    @Transactional
    public Diary updateDiary(Long diaryId, UserEntity user, DiaryEntityPatchRequestBody requestBody) {
        DiaryEntity diaryEntity = getDiaryEntityByDiaryId(diaryId);

        if (!diaryEntity.getUser().getUsername().equals(user.getUsername())) {
            throw new DiaryNotFoundException("본인이 작성한 일기만 수정할 수 있습니다.");
        }

        if (requestBody.type() == DiaryType.QUESTION) {
            QuestionAnswerEntity answer =
                    questionAnswerService.updateQuestionAnswer(diaryId, requestBody);
            diaryEntity.setQuestionAnswer(answer);
        }
        else if (requestBody.type() == DiaryType.FREE) {
            FreeDiaryContentEntity content =
                    freeDiaryContentService.updateQuestionAnswer(diaryId, requestBody);
            diaryEntity.setFreeDiaryContent(content);
        }
        else {
            throw new DiaryNotFoundException();
        }
        return Diary.from(diaryEntity);
    }

    @Transactional
    public void deleteDiary(Long diaryId, UserEntity user) {
        DiaryEntity diaryEntity = getDiaryEntityByDiaryId(diaryId);
        if (!diaryEntity.getUser().getUsername().equals(user.getUsername())) {
            throw new DiaryNotFoundException("본인이 작성한 일기만 삭제할 수 있습니다.");
        }
        diaryEntityRepository.delete(diaryEntity);
    }

    @Transactional
    public void deleteDiaryAdmin(Long diaryId, AdminEntity admin) {
        DiaryEntity diaryEntity = getDiaryEntityByDiaryId(diaryId);
        if (!Role.ADMIN.equals(admin.getRole())) {
            throw new DiaryNotFoundException("관리자 권한이 없습니다.");
        }
        diaryEntityRepository.delete(diaryEntity);
    }
}
