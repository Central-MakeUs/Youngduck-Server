package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.screening.dto.request.PostScreeningRequest;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.HostInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class PatchScreeningUseCase {
    private final ScreeningAdaptor screeningAdaptor;

    @Transactional
    public Screening execute(Long screenId,PostScreeningRequest request) {
        final HostInfo hostInfo = HostInfo.of(
                request.getHostName(),
                request.getHostPhoneNumber(),
                request.getHostEmail()
        );
        final Screening newScreening = Screening.of(
                request.getScreeningTitle(),
                request.getPosterImgUrl(),
                hostInfo,
                request.getLocation(),
                request.getFormUrl(),
                request.getInformation(),
                request.getScreeningStartDate(),
                request.getScreeningEndDate(),
                request.getScreeningStartTime(),
                request.isHasAgreed(),
                request.getCategory()
        );
        proceed(screenId,newScreening);

        return newScreening;
    }

    private void proceed(Long screenId, Screening newScreening) {
        Screening screen = screeningAdaptor.findById(screenId);
        screen.update(newScreening);
    }
}
