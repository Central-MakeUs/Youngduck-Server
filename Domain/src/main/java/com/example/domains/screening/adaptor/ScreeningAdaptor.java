package com.example.domains.screening.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.screening.entity.QScreening;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.entity.dto.QScreeningResponseDto;
import com.example.domains.screening.entity.dto.ScreeningResponseDto;
import com.example.domains.screening.enums.Category;
import com.example.domains.screening.repository.ScreeningRepository;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.repository.UserScreeningRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class ScreeningAdaptor {
    private final ScreeningRepository screeningRepository;
    private final UserScreeningRepository userScreeningRepository;
    private final JPAQueryFactory jpaQueryFactory;


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
                        )
                )
                .orderBy(QScreening.screening.screeningStartDate.asc())
                .limit(3)
                .fetch();
    }

    public List<ScreeningResponseDto> getMostRecentScreening() {
        return jpaQueryFactory
                .select(new QScreeningResponseDto(
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
                .orderBy(QScreening.screening.createdAt.desc())
                .limit(3)
                .fetch();
    }


    public Slice<Screening> searchScreenings(String title, Category category, Pageable pageable) {
        return screeningRepository.querySliceScreening(title, category, pageable);
    }
}
