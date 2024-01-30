package com.example.api.Popcorn.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.block.adaptor.BlockAdaptor;
import com.example.domains.popcornUser.adaptor.PopcornUserAdaptor;
import com.example.domains.popcornUser.entity.PopcornUser;
import com.example.domains.popcornUser.repository.PopcornUserRepository;
import com.example.domains.screeningReview.entity.QScreeningReview;
import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class PostPopcornReviewComplainUseCase {
    private final BlockAdaptor blockAdaptor;
    private final PopcornUserAdaptor popcornUserAdaptor;
    private final PopcornUserRepository popcornUserRepository;
    public void execute(Long reviewId) {
        Long userId = SecurityUtil.getCurrentUserId();
        popcornUserAdaptor.postComplain(reviewId,userId);
    }


}
