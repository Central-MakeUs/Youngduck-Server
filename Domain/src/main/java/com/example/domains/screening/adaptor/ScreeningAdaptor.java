package com.example.domains.screening.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.common.util.SliceResponse;
import com.example.domains.screening.entity.QScreening;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.entity.dto.QScreeningResponseDto;
import com.example.domains.screening.entity.dto.ScreeningCountDto;
import com.example.domains.screening.entity.dto.ScreeningResponseDto;
import com.example.domains.screening.enums.Category;
import com.example.domains.screening.repository.ScreeningRepository;
import com.example.domains.screeningReview.entity.QScreeningReview;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.QUserScreening;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.repository.UserScreeningRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class ScreeningAdaptor {
    private final ScreeningRepository screeningRepository;
    private final UserScreeningRepository userScreeningRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final UserScreeningAdaptor userScreeningAdaptor;


    public Screening save(Screening screening) {
        Screening savedScreening = screeningRepository.save(screening);
        return savedScreening;
    }

    public void saveUserScreening(UserScreening userScreening) {
        userScreeningRepository.save(userScreening);
    }

    public Screening findById(Long id) {
        Screening screening = screeningRepository.findById(id).get();
        return screening;
    }

    @Transactional
    public void changePrivateStatus(Long screeningId) {
       Screening screening = screeningRepository.findById(screeningId).get();
       screening.updatePrivacy(!screening.isPrivate());
    }

    public List<ScreeningResponseDto> getTopThree() {
        ZonedDateTime now = ZonedDateTime.now();
        LocalDate startOfWeek = now.toLocalDate().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6); // Assuming Sunday is the last day of the week
        return jpaQueryFactory.select(new QScreeningResponseDto(
                QScreening.screening.id,
                        QScreening.screening.title,
                        QScreening.screening.posterImgUrl,
                        QScreening.screening.hostInfo.hostName,
                        QScreening.screening.hostInfo.hostEmail,
                        QScreening.screening.hostInfo.hostPhoneNumber,
                        QScreening.screening.location,
                        QScreening.screening.participationUrl,
                        QScreening.screening.information,
                        QScreening.screening.hasAgreed,
                        QScreening.screening.category,
                        QScreening.screening.screeningStartDate,
                        QScreening.screening.screeningEndDate,
                        QScreening.screening.screeningStartTime,
                        QScreening.screening.isPrivate
                ))
                .from(QScreening.screening)
                .where(
                        QScreening.screening.screeningStartDate.between(
                                startOfWeek.atStartOfDay(),
                                endOfWeek.atTime(23, 59, 59)
                        ),
                        QScreening.screening.isPrivate.eq(false)
                )
                .orderBy(QScreening.screening.screeningStartDate.asc())
                .limit(3)
                .fetch();
    }

    public List<ScreeningResponseDto> getMostRecentScreening() {
        return jpaQueryFactory
                .select(new QScreeningResponseDto(
                        QScreening.screening.id,
                        QScreening.screening.title,
                        QScreening.screening.posterImgUrl,
                        QScreening.screening.hostInfo.hostName,
                        QScreening.screening.hostInfo.hostEmail,
                        QScreening.screening.hostInfo.hostPhoneNumber,
                        QScreening.screening.location,
                        QScreening.screening.participationUrl,
                        QScreening.screening.information,
                        QScreening.screening.hasAgreed,
                        QScreening.screening.category,
                        QScreening.screening.screeningStartDate,
                        QScreening.screening.screeningEndDate,
                        QScreening.screening.screeningStartTime,
                        QScreening.screening.isPrivate
                ))
                .from(QScreening.screening)
                .where(QScreening.screening.isPrivate.eq(false))
                .orderBy(QScreening.screening.createdAt.desc())
                .limit(3)
                .fetch();
    }


    public SliceResponse<Screening> searchScreenings(String title, Category category, Pageable pageable) {
        return screeningRepository.querySliceScreening(title, category, pageable);
    }

    public SliceResponse<Screening> searchByStartDate(String title, Category category, Pageable pageable) {
        return screeningRepository.querySliceScreeningByDate(title, category, pageable);
    }

    public List<ScreeningResponseDto> getMostReviewed() {
        //review에 있는userScreening join까지 해서 특정 스크리닝에 있는 리뷰 수 중에서 top3반환해주게 짜줘
        return jpaQueryFactory
                .select(new QScreeningResponseDto(
                        QScreening.screening.id,
                        QScreening.screening.title,
                        QScreening.screening.posterImgUrl,
                        QScreening.screening.hostInfo.hostName,
                        QScreening.screening.hostInfo.hostEmail,
                        QScreening.screening.hostInfo.hostPhoneNumber,
                        QScreening.screening.location,
                        QScreening.screening.participationUrl,
                        QScreening.screening.information,
                        QScreening.screening.hasAgreed,
                        QScreening.screening.category,
                        QScreening.screening.screeningStartDate,
                        QScreening.screening.screeningEndDate,
                        QScreening.screening.screeningStartTime,
                        QScreening.screening.isPrivate
                ))
                .from(QScreening.screening)
                .leftJoin(QUserScreening.userScreening).on(QScreening.screening.eq(QUserScreening.userScreening.screening))
                .leftJoin(QScreeningReview.screeningReview).on(QUserScreening.userScreening.eq(QScreeningReview.screeningReview.userScreening))
                .where(QScreening.screening.isPrivate.eq(false))
                .groupBy(QScreening.screening.id, QUserScreening.userScreening.id)
                .orderBy(QScreeningReview.screeningReview.count().desc())
                .limit(3)
                .fetch();
    }

    public List<Screening> getBookmarkedScreenings(Long userId) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<Screening> bookmarkedScreenings = jpaQueryFactory
                .selectDistinct(QScreeningReview.screeningReview.userScreening.screening)
                .from(QScreeningReview.screeningReview)
                .join(QUserScreening.userScreening)
                .on(
                        QUserScreening.userScreening.id.eq(QScreeningReview.screeningReview.userScreening.id),
                        QUserScreening.userScreening.isHost.eq(false),
                        QUserScreening.userScreening.user.id.eq(userId),
                        QUserScreening.userScreening.isBookmarked.eq(true),
                        QScreeningReview.screeningReview.userScreening.screening.screeningStartDate.before(currentDateTime),
                        QScreeningReview.screeningReview.userScreening.screening.isPrivate.eq(false)
                )
                .fetch();

        return bookmarkedScreenings;
    }


    public List<Screening> getUpcomingScreenings(Long userId) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<Screening> upcomingScreenings = jpaQueryFactory
                .selectDistinct(QScreeningReview.screeningReview.userScreening.screening)
                .from(QScreeningReview.screeningReview)
                .join(QUserScreening.userScreening)
                .on(
                        QUserScreening.userScreening.id.eq(QScreeningReview.screeningReview.userScreening.id),
                        QUserScreening.userScreening.isHost.eq(false),
                        QUserScreening.userScreening.user.id.eq(userId),
                        QUserScreening.userScreening.isBookmarked.eq(true),
                        QScreeningReview.screeningReview.userScreening.screening.screeningStartDate.after(currentDateTime),
                        QScreeningReview.screeningReview.userScreening.screening.isPrivate.eq(false)
                )
                .fetch();

        return upcomingScreenings;
    }


    public List<Screening> findByStartDate(LocalDateTime minusDays) {
        return screeningRepository.findByScreeningStartDate(minusDays);
    }

    @Transactional
    public void incrementNegativeIffy(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.iffy, screening.getNegativeCount().getIffy() + 1)
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadAngle(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badAngle, qScreening.negativeCount.badAngle.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadDetail(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badDetail, qScreening.negativeCount.badDetail.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadColor(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badColor, qScreening.negativeCount.badColor.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadCustom(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badCustom, qScreening.negativeCount.badCustom.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

   @Transactional
    public void incrementNegativeBadMusic(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badMusic, qScreening.negativeCount.badMusic.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadSound(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badSound, qScreening.negativeCount.badSound.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadEnding(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badEnding, qScreening.negativeCount.badEnding.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeNoDetail(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.noDetail, qScreening.negativeCount.noDetail.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadTopic(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badTopic, qScreening.negativeCount.badTopic.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadActing(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badActing, qScreening.negativeCount.badActing.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }
    @Transactional
    public void incrementNegativeBadCasting(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badCasting, qScreening.negativeCount.badCasting.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementNegativeBadEditing(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.negativeCount.badEditing, qScreening.negativeCount.badEditing.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementNegativeEndingLoose(Screening screening) {
        QScreening qScreening = QScreening.screening;
        jpaQueryFactory
                .update(qScreening)
                .set(qScreening.negativeCount.endingLoose, qScreening.negativeCount.endingLoose.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveCineMaster(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.cineMaster, qScreening.positiveCount.cineMaster.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveGreatFilming(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.greatFilming, qScreening.positiveCount.greatFilming.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositivePom(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.pom, qScreening.positiveCount.pom.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveAnimationIsGood(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.animationIsGood, qScreening.positiveCount.animationIsGood.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveArtIsGood(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.artIsGood, qScreening.positiveCount.artIsGood.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveCustom(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.custom, qScreening.positiveCount.custom.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveMusic(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.music, qScreening.positiveCount.music.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveTopicIsGood(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.topicIsGood, qScreening.positiveCount.topicIsGood.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveLinesAreGood(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.linesAreGood, qScreening.positiveCount.linesAreGood.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveEndingIsGood(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.endingIsGood, qScreening.positiveCount.endingIsGood.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveCastingIsGood(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.castingIsGood, qScreening.positiveCount.castingIsGood.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveActingIsGood(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.actingIsGood, qScreening.positiveCount.actingIsGood.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementPositiveChemistryIsGood(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.positiveCount.chemistryIsGood, qScreening.positiveCount.chemistryIsGood.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }


    @Transactional
    public void incrementScreeningReview(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.movieReviewCountPos, qScreening.movieReviewCountPos.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void decrementScreeningReview(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.movieReviewCountNeg, qScreening.movieReviewCountNeg.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementLocationReview(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.locationCountPos, qScreening.locationCountPos.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void decrementLocationReview(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.locationCountNeg, qScreening.locationCountNeg.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementServiceReview(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.serviceCountPos, qScreening.serviceCountPos.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void decrementServiceReview(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.serviceCountNeg, qScreening.serviceCountNeg.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }

    @Transactional
    public void incrementAfterScreening(Screening screening) {
        QScreening qScreening = QScreening.screening;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qScreening);

        updateClause
                .set(qScreening.screeningRate, qScreening.screeningRate.add(1))
                .where(qScreening.id.eq(screening.getId()))
                .execute();
    }


}
