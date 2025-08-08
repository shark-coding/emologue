package com.project.emologue.repository;

import com.project.emologue.model.dto.DailyEmotionDto;
import com.project.emologue.model.dto.EmotionStatisticsDto;
import com.project.emologue.model.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
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

    @Override
    public List<EmotionStatisticsDto> getJobsEmotionStatistics() {
        return fetchEmotionStatusByJobs();
    }

    @Override
    public List<DailyEmotionDto> getEmotionTrendMonthly(Long userId) {
        LocalDate fromDate = LocalDate.now().minusMonths(1);
        return fetchDailyEmotionTrend(userId, fromDate);
    }

    @Override
    public List<DailyEmotionDto> getEmotionTrendWeekly(Long userId) {
        LocalDate fromDate = LocalDate.now().minusWeeks(1);
        return fetchDailyEmotionTrend(userId, fromDate);
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

    private List<EmotionStatisticsDto> fetchEmotionStatusByJobs() {
        QUserEntity user = QUserEntity.userEntity;
        QFreeDiaryContentEntity free = QFreeDiaryContentEntity.freeDiaryContentEntity;
        QQuestionAnswerEntity question = QQuestionAnswerEntity.questionAnswerEntity;
        QDiaryEntity diary = QDiaryEntity.diaryEntity;
        QAIPredictedEmotionEntity ai = QAIPredictedEmotionEntity.aIPredictedEmotionEntity;
        QEmotionEntity emotion = QEmotionEntity.emotionEntity;


        // freeDiary 감정 통계
        List<Tuple> freeDiaryResults = queryFactory
                .select(user.job.jobname, ai.emotionType.stringValue(), ai.emotionType.count())
                .from(diary)
                .leftJoin(diary.user, user)
                .leftJoin(diary.freeDiaryContent, free)
                .leftJoin(free.aiPredictedEmotionEntity, ai)
                .where(ai.emotionType.isNotNull())
                .groupBy(user.job.jobname, ai.emotionType)
                .fetch();

        // questionAnswer 감정 통계
        List<Tuple> questionResults = queryFactory
                .select(user.job.jobname, emotion.emotionType.stringValue(), emotion.emotionType.count())
                .from(diary)
                .leftJoin(diary.user, user)
                .leftJoin(diary.questionAnswer, question)
                .leftJoin(question.firstAnswer, emotion)
                .where(emotion.emotionType.isNotNull())
                .groupBy(user.job.jobname, emotion.emotionType)
                .fetch();

        // 결과 합치기
        Map<String, Map<String, Long>> jobEmotionMap = new HashMap<> ();

        for (Tuple tuple : freeDiaryResults) {
            String job = tuple.get(0, String.class);
            String emotionType = tuple.get(1, String.class);
            Long count = tuple.get(2, Long.class);
            jobEmotionMap
                    .computeIfAbsent(job, k -> new HashMap<>())
                    .merge(emotionType, count, Long::sum);
        }

        for (Tuple tuple : questionResults) {
            String job = tuple.get(0, String.class);
            String emotionType = tuple.get(1, String.class);
            Long count = tuple.get(2, Long.class);
            jobEmotionMap
                    .computeIfAbsent(job, k -> new HashMap<>())
                    .merge(emotionType, count, Long::sum);
        }

        // 비율 계산 및 DTO 변환
        List<EmotionStatisticsDto> result = new ArrayList<>();

        for (Map.Entry<String, Map<String, Long>> jobEntry : jobEmotionMap.entrySet()) {
            String job = jobEntry.getKey();
            Map<String, Long> emotionCounts = jobEntry.getValue();
            long total = emotionCounts.values().stream().mapToLong(Long::longValue).sum();

            for (Map.Entry<String, Long> e : emotionCounts.entrySet()) {
                String emotionType = e.getKey();
                Long count = e.getValue();
                double ratio = total > 0 ? Math.round(count * 10000.0 / total)/100.0 : 0.0;

                result.add(new EmotionStatisticsDto(job, emotionType, count, ratio));
            }
        }

        return result;
    }

    private List<DailyEmotionDto> fetchDailyEmotionTrend(Long userId, LocalDate fromDate) {
        QDiaryEntity diary = QDiaryEntity.diaryEntity;
        QFreeDiaryContentEntity free = QFreeDiaryContentEntity.freeDiaryContentEntity;
        QQuestionAnswerEntity question = QQuestionAnswerEntity.questionAnswerEntity;
        QAIPredictedEmotionEntity ai = QAIPredictedEmotionEntity.aIPredictedEmotionEntity;
        QEmotionEntity emotion = QEmotionEntity.emotionEntity;

        // DATE Template
        DateTemplate<LocalDate> freeDateTemplate = Expressions.dateTemplate(
                LocalDate.class,
                "DATE({0})",
                diary.createdDateTime
        );

        // freeDiary
        List<Tuple> freeResults = queryFactory
                .select(
                        freeDateTemplate,
                        ai.emotionType.stringValue(),
                        ai.count()
                )
                .from(diary)
                .leftJoin(diary.freeDiaryContent, free)
                .leftJoin(free.aiPredictedEmotionEntity, ai)
                .where(
                        diary.user.userId.eq(userId),
                        diary.createdDateTime.goe(fromDate),
                        ai.emotionType.isNotNull()
                )
                .groupBy(freeDateTemplate, ai.emotionType)
                .fetch();

        // DATE Template
        DateTemplate<LocalDate> questionDateTemplate = Expressions.dateTemplate(
                LocalDate.class,
                "DATE({0})",
                diary.createdDateTime
        );

        // Question 감정
        List<Tuple> questionResults = queryFactory
                .select(
                        questionDateTemplate,
                        emotion.emotionType.stringValue(),
                        emotion.count()
                )
                .from(diary)
                .leftJoin(diary.questionAnswer, question)
                .leftJoin(question.firstAnswer, emotion)
                .where(
                        diary.user.userId.eq(userId),
                        diary.createdDateTime.goe(fromDate),
                        emotion.emotionType.isNotNull()
                )
                .groupBy(questionDateTemplate, emotion.emotionType)
                .fetch();

        // 결과 합치기
        Map<LocalDate, Map<String, Long>> dailyMap = new TreeMap<>(); // TreeMap: 날짜 순 정렬

        List<Tuple> allResults = new ArrayList<>();
        allResults.addAll(freeResults);
        allResults.addAll(questionResults);

        for (Tuple tuple : allResults) {

            Object dateObj = tuple.get(0, Objects.class);

            LocalDate date;
            if (dateObj instanceof LocalDate) {
                date = (LocalDate) dateObj;
            } else if (dateObj instanceof java.sql.Date) {
                date = ((java.sql.Date) dateObj).toLocalDate();
            } else if (dateObj instanceof java.sql.Timestamp) {
                date = ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate();
            } else if (dateObj instanceof String) {
                // 혹시 문자열이면 파싱 처리 (예: "2025-08-08")
                date = LocalDate.parse((String) dateObj);
            } else {
                // 예상치 못한 타입
                System.out.println("dateObj 타입 알 수 없음: " + dateObj.getClass());
                continue;
            }

            String type = tuple.get(1, String.class);
            Long count = tuple.get(2, Long.class);

            dailyMap
                    .computeIfAbsent(date, d -> new HashMap<>())
                    .merge(type, count, Long::sum);

        }

        return dailyMap.entrySet().stream()
                .map(entry -> new DailyEmotionDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
