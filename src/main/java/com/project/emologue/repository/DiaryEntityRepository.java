package com.project.emologue.repository;

import com.project.emologue.model.entity.DiaryEntity;
import com.project.emologue.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DiaryEntityRepository extends JpaRepository<DiaryEntity, Long> {
    Optional<DiaryEntity> findByDiaryId(Long diaryId);
    Optional<DiaryEntity> findAllByUser(UserEntity user);

    boolean existsByUserAndCreatedDateTime(UserEntity user, LocalDate date);
}
