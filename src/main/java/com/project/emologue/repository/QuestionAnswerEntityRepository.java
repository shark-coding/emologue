package com.project.emologue.repository;

import com.project.emologue.model.entity.QuestionAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionAnswerEntityRepository extends JpaRepository<QuestionAnswerEntity, Long> {
    Optional<QuestionAnswerEntity> findByQuestionAnswerId(Long questionAnswerId);

    Optional<QuestionAnswerEntity> findByDiary_DiaryId(Long diaryId);
}
