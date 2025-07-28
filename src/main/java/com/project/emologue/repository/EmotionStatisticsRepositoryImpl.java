package com.project.emologue.repository;

import com.project.emologue.model.dto.EmotionStatisticsDto;
import com.project.emologue.model.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EmotionStatisticsRepositoryImpl implements EmotionStatisticsRepository {

    private JPAQueryFactory queryFactory;

    public EmotionStatisticsRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<EmotionStatisticsDto> getWeeklyEmotionStatistics(Long userId) {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        return fetchEmotionStatus(userId, oneWeekAgo);
    }

    @Override
    public List<EmotionStatisticsDto> getMonthlyEmotionStatistics(Long userId) {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return fetchEmotionStatus(userId, oneMonthAgo);
    }

    private List<EmotionStatisticsDto> fetchEmotionStatus(Long userId, LocalDate fromDate) {
        QFreeDiaryContentEntity free = QFreeDiaryContentEntity.freeDiaryContentEntity;
        QQuestionAnswerEntity question = QQuestionAnswerEntity.questionAnswerEntity;
        QDiaryEntity diary = QDiaryEntity.diaryEntity;
        QAIPredictedEmotionEntity ai = QAIPredictedEmotionEntity.aIPredictedEmotionEntity;
        QEmotionEntity emotion = QEmotionEntity.emotionEntity;

        // freeDiary 감정 통계
        List<Tuple> freeDiaryResults = queryFactory
                .select(ai.emotionType.stringValue(), ai.emotionType.count())
                .from(diary)
                .leftJoin(diary.freeDiaryContent, free)
                .leftJoin(free.aiPredictedEmotionEntity, ai)
                .where(
                        diary.user.userId.eq(userId),
                        diary.createdDateTime.goe(fromDate),
                        ai.emotionType.isNotNull()
                )
                .groupBy(ai.emotionType)
                .fetch();

        // questionAnswer 감정 통계
        List<Tuple> questionResults = queryFactory
                .select(emotion.emotionType.stringValue(), emotion.emotionType.count())
                .from(diary)
                .leftJoin(diary.questionAnswer, question)
                .leftJoin(question.firstAnswer, emotion)
                .where(
                        diary.user.userId.eq(userId),
                        diary.createdDateTime.goe(fromDate),
                        emotion.emotionType.isNotNull()
                )
                .groupBy(emotion.emotionType)
                .fetch();

        // 결과 합치기
        Map<String, Long> emotionCountMap = new HashMap<> ();

        for (Tuple tuple : freeDiaryResults) {
            String type = tuple.get(0, String.class);
            Long count = tuple.get(1, Long.class);
            emotionCountMap.merge(type, count, Long::sum);
        }

        for (Tuple tuple : questionResults) {
            String type = tuple.get(0, String.class);
            Long count = tuple.get(1, Long.class);
            emotionCountMap.merge(type, count, Long::sum);
        }

        long total = emotionCountMap.values().stream().mapToLong(Long::longValue).sum();

        return emotionCountMap.entrySet().stream()
                .map(e -> new EmotionStatisticsDto(e.getKey(), e.getValue(), total > 0 ? (e.getValue() * 100.0 / total) : 0.0))
                .collect(Collectors.toList());

    }
}
