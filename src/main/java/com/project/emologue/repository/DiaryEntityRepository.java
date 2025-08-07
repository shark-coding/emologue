package com.project.emologue.repository;

import com.project.emologue.model.diary.DiaryType;
import com.project.emologue.model.entity.DiaryEntity;
import com.project.emologue.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryEntityRepository extends JpaRepository<DiaryEntity, Long> {
    Optional<DiaryEntity> findByDiaryId(Long diaryId);
    List<DiaryEntity> findAllByUser(UserEntity user);

    boolean existsByUserAndCreatedDateTime(UserEntity user, LocalDate date);

    List<DiaryEntity> findAllByUserAndType(UserEntity user, DiaryType type);
    List<DiaryEntity> findAllByType(DiaryType type);

}
