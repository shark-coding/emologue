package com.project.emologue.repository;

import com.project.emologue.model.entity.FreeDiaryContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeDiaryContentEntityRepository extends JpaRepository<FreeDiaryContentEntity, Long> {
    Optional<FreeDiaryContentEntity> findByFreeDiaryContentId(Long freeDiaryContentId);
    Optional<FreeDiaryContentEntity> findByDiary_DiaryId(Long diaryId);
}
