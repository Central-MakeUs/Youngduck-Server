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
import com.example.s3.service.S3PresignedUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@UseCase
@RequiredArgsConstructor
public class ScreeningUploadUseCase {
    private final UserValidator userValidator;
    private final ScreeningAdaptor screeningAdaptor;
    private final UserAdaptor userAdaptor;
    private final UserScreeningAdaptor userScreeningAdaptor;
    private final S3PresignedUrlService s3UploadService;
    public String uploadImage(MultipartFile file) throws IOException {
        // S3에 이미지 파일 업로드 및 업로드된 파일의 URL 생성
        String imageUrl = s3UploadService.upload(file);
        return imageUrl;
    }

    public Screening execute(PostScreeningRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);

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
