package com.example.domains.popcorn.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.popcorn.entity.Popcorn;
import com.example.domains.popcorn.entity.QPopcorn;
import com.example.domains.popcorn.entity.dto.PopcornKeywordResponseDto;
import com.example.domains.popcorn.entity.dto.QPopcornKeywordResponseDto;
import com.example.domains.popcorn.repository.PopcornRepository;
import com.example.domains.recommendedPopcorn.entity.QRecommendedPopcorn;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.domains.popcorn.entity.QPopcorn.popcorn;

@Adaptor
@RequiredArgsConstructor
public class PopcornAdaptor {
    private final PopcornRepository popcornRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private static LocalDate today = LocalDate.now();
    private static LocalDate startOfLastWeek = today.minusDays(today.getDayOfWeek().getValue() + 6); // 지난 주의 월요일
    private static LocalDate endOfLastWeek = startOfLastWeek.plusDays(6); // 지난 주의 일요일
    private static LocalDate startOfThisWeek = today.with(DayOfWeek.MONDAY);
    private static LocalDate endOfThisWeek = today.with(DayOfWeek.SATURDAY);

    @Transactional
    public void save(Popcorn popcorn){
        popcornRepository.save(popcorn);
    }


    public void saveToPopcorn() {
        List<RecommendedPopcorn> listRecommendedPopcorn = findTopThree();
        for (RecommendedPopcorn rp : listRecommendedPopcorn) {
            Popcorn movie = convertToPopcorn(rp);
            save(movie);
        }
    }

    public Popcorn convertToPopcorn(RecommendedPopcorn recommendedPopcorn){
        final Popcorn popcorn = Popcorn.of(recommendedPopcorn.getMovieId(),recommendedPopcorn.getMovieTitle(),recommendedPopcorn.getImageUrl(),recommendedPopcorn.getMovieDetail(),recommendedPopcorn.getMovieDirector(),recommendedPopcorn.getRecommendationReason(),recommendedPopcorn.getRecommendationCount());
        return popcorn;
    }

    public List<RecommendedPopcorn> findTopThree() {
        QRecommendedPopcorn recommendedPopcorn = QRecommendedPopcorn.recommendedPopcorn;

        return jpaQueryFactory
                .selectFrom(recommendedPopcorn)
                .where(recommendedPopcorn.createdAt.between(startOfThisWeek.atStartOfDay(), endOfThisWeek.atTime(23, 59, 59)))
                .orderBy(
                        recommendedPopcorn.recommendationCount.desc(),
                        recommendedPopcorn.createdAt.desc()
                )
                .limit(3)
                .fetch();
    }

    public List<RecommendedPopcorn> findTopThreeTest() {
        QRecommendedPopcorn recommendedPopcorn = QRecommendedPopcorn.recommendedPopcorn;

        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);

        return jpaQueryFactory
                .selectFrom(recommendedPopcorn)
                .where(
                        recommendedPopcorn.createdAt.after(tenMinutesAgo),
                        recommendedPopcorn.recommendationCount.gt(0)
                )
                .orderBy(
                        recommendedPopcorn.recommendationCount.desc(),
                        recommendedPopcorn.createdAt.desc()
                )
                .limit(3)
                .fetch();
    }

    public List<Popcorn> findLastWeekPopcorns() {
        QPopcorn popcorn = QPopcorn.popcorn;

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startWeek = today.with(DayOfWeek.SUNDAY).minusWeeks(1);
        LocalDateTime endThisWeek = today.with(DayOfWeek.SATURDAY);

        return jpaQueryFactory
                .selectFrom(popcorn)
                .where(popcorn.createdAt.between(startWeek, endThisWeek.withHour(23).withMinute(59).withSecond(59)))
                .orderBy(
                        popcorn.recommendationCount.desc(),
                        popcorn.createdAt.desc()
                )
                .limit(3)
                .fetch();
    }

    public Popcorn findById(Long popcornId){
      return popcornRepository.findById(popcornId).get();
    }
    public Optional<Popcorn> checkIfExists(Long popcornId){
        return popcornRepository.findById(popcornId);
    }

    @Transactional
    public void incrementPositiveCineMaster(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.cineMaster, qpopcorn.popcornPostiveCount.cineMaster.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveGreatFilming(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.greatFilming, qpopcorn.popcornPostiveCount.greatFilming.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositivePom(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.pom, qpopcorn.popcornPostiveCount.pom.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveAnimationIsGood(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.animationIsGood, qpopcorn.popcornPostiveCount.animationIsGood.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveArtIsGood(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.artIsGood, qpopcorn.popcornPostiveCount.artIsGood.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveCustom(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.custom, qpopcorn.popcornPostiveCount.custom.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveMusic(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.music, qpopcorn.popcornPostiveCount.music.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveTopicIsGood(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.topicIsGood, qpopcorn.popcornPostiveCount.topicIsGood.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveLinesAreGood(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.linesAreGood, qpopcorn.popcornPostiveCount.linesAreGood.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveEndingIsGood(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.endingIsGood, qpopcorn.popcornPostiveCount.endingIsGood.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveCastingIsGood(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.castingIsGood, qpopcorn.popcornPostiveCount.castingIsGood.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveActingIsGood(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.actingIsGood, qpopcorn.popcornPostiveCount.actingIsGood.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveChemistryIsGood(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.chemistryIsGood, qpopcorn.popcornPostiveCount.chemistryIsGood.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeIffy(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.iffy, qpopcorn.popcornNegativeCount.iffy.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadEditing(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badEditing, qpopcorn.popcornNegativeCount.badEditing.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadAngle(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badAngle, qpopcorn.popcornNegativeCount.badAngle.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadDetail(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badDetail, qpopcorn.popcornNegativeCount.badDetail.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadColor(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badColor, qpopcorn.popcornNegativeCount.badColor.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadCustom(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badCustom, qpopcorn.popcornNegativeCount.badCustom.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadMusic(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badMusic, qpopcorn.popcornNegativeCount.badMusic.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadSound(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badSound, qpopcorn.popcornNegativeCount.badSound.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadEnding(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badEnding, qpopcorn.popcornNegativeCount.badEnding.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeEndingLoose(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.endingLoose, qpopcorn.popcornNegativeCount.endingLoose.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeNoDetail(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.noDetail, qpopcorn.popcornNegativeCount.noDetail.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadTopic(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badTopic, qpopcorn.popcornNegativeCount.badTopic.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadActing(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badActing, qpopcorn.popcornNegativeCount.badActing.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadCasting(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornNegativeCount.badCasting, qpopcorn.popcornNegativeCount.badCasting.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveSetIsArt(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.setIsArt, qpopcorn.popcornPostiveCount.setIsArt.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveOst(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.ost, qpopcorn.popcornPostiveCount.ost.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
    @Transactional
    public void incrementPositiveWrittenByGod(Popcorn popcorn) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornPostiveCount.writtenByGod, qpopcorn.popcornPostiveCount.writtenByGod.add(1))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();
    }
//
public PopcornKeywordResponseDto getTopRatedCounts(Long popcornId) {
    PopcornKeywordResponseDto resultList = jpaQueryFactory
            .select(new QPopcornKeywordResponseDto(
                    popcorn.popcornPostiveCount.cineMaster,
                    popcorn.popcornPostiveCount.greatFilming,
                    popcorn.popcornPostiveCount.animationIsGood,
                    popcorn.popcornPostiveCount.artIsGood,
                    popcorn.popcornPostiveCount.pom,
                    popcorn.popcornPostiveCount.setIsArt,
                    popcorn.popcornPostiveCount.custom,
                    popcorn.popcornPostiveCount.music,
                    popcorn.popcornPostiveCount.ost,
                    popcorn.popcornPostiveCount.writtenByGod,
                    popcorn.popcornPostiveCount.topicIsGood,
                    popcorn.popcornPostiveCount.linesAreGood,
                    popcorn.popcornPostiveCount.endingIsGood,
                    popcorn.popcornPostiveCount.castingIsGood,
                    popcorn.popcornPostiveCount.actingIsGood,
                    popcorn.popcornPostiveCount.chemistryIsGood,
                    popcorn.popcornNegativeCount.iffy,
                    popcorn.popcornNegativeCount.badEditing,
                    popcorn.popcornNegativeCount.badAngle,
                    popcorn.popcornNegativeCount.badDetail,
                    popcorn.popcornNegativeCount.badColor,
                    popcorn.popcornNegativeCount.badCustom,
                    popcorn.popcornNegativeCount.badMusic,
                    popcorn.popcornNegativeCount.badSound,
                    popcorn.popcornNegativeCount.badEnding,
                    popcorn.popcornNegativeCount.endingLoose,
                    popcorn.popcornNegativeCount.noDetail,
                    popcorn.popcornNegativeCount.badTopic,
                    popcorn.popcornNegativeCount.badActing,
                    popcorn.popcornNegativeCount.badCasting
            ))
            .from(popcorn)
            .where(popcorn.id.eq(popcornId))
            .fetchOne();


    return resultList;

}

    public List<Popcorn> findPastHoursPopcorns() {
        QPopcorn popcorn = QPopcorn.popcorn;

        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        return jpaQueryFactory
                .selectFrom(popcorn)
                .where(
                        popcorn.createdAt.after(oneHourAgo),
                        popcorn.recommendationCount.gt(0)
                )
                .orderBy(
                        popcorn.recommendationCount.desc(),
                        popcorn.createdAt.desc()
                )
                .limit(3)
                .fetch();
    }

    public void saveToPopcornTest() {
        List<RecommendedPopcorn> listRecommendedPopcorn = findTopThreeTest();
        for (RecommendedPopcorn rp : listRecommendedPopcorn) {
            Popcorn movie = convertToPopcorn(rp);
            save(movie);
        }
    }


}
