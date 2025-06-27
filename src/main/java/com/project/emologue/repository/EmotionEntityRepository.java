package com.project.emologue.repository;

import com.project.emologue.model.entity.EmotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmotionEntityRepository extends JpaRepository<EmotionEntity, Long> {
    Optional<EmotionEntity> findByName(String name);
    Optional<EmotionEntity> findByEmotionId(Long emotionId);
}
