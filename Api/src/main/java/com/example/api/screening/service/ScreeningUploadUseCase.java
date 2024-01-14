package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.request.PostScreeningRequest;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.HostInfo;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.repository.UserRepository;
import com.example.domains.user.validator.UserValidator;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ScreeningUploadUseCase {
    private final UserValidator userValidator;
    private final ScreeningAdaptor screeningAdaptor;
    private final UserRepository userRepository;
    private final UserAdaptor userAdaptor;
    private final UserScreeningAdaptor userScreeningAdaptor;

    public Screening execute(PostScreeningRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);
        System.out.println(request.getHostName());
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
        System.out.print("finished");
        Screening screen = screeningAdaptor.save(newScreening);
        process(screen,userId);

        return newScreening;
    }

    private void process(Screening screen, Long userId){
        User user = userAdaptor.findById(userId);
        final UserScreening userScreening = UserScreening.of(
                true,
                false,
                true,
                user,
                screen
        );
        userScreeningAdaptor.save(userScreening);
    }
    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }
}
