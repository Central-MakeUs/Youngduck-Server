package com.example.domains.popcornUser.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.block.adaptor.BlockAdaptor;
import com.example.domains.popcorn.entity.Popcorn;
import com.example.domains.popcorn.entity.QPopcorn;
import com.example.domains.popcornUser.entity.PopcornUser;
import com.example.domains.popcornUser.entity.QPopcornUser;
import com.example.domains.popcornUser.repository.PopcornUserRepository;
import com.example.domains.screeningReview.entity.QScreeningReview;
import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.user.entity.User;
import com.google.api.client.util.SecurityUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class PopcornUserAdaptor {
    private final PopcornUserRepository popcornUserRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final BlockAdaptor blockAdaptor;
    public void save(PopcornUser popcornUser) {
        popcornUserRepository.save(popcornUser);
    }

    @Transactional
    public void incrementPopcornRate(Popcorn popcorn, int i) {
        QPopcorn qpopcorn = QPopcorn.popcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qpopcorn);

        updateClause
                .set(qpopcorn.popcornRate, qpopcorn.popcornRate.add(i))
                .where(qpopcorn.id.eq(popcorn.getId()))
                .execute();

    }

    public List<PopcornUser> findAllByPopcornId(Long popcornId) {
        return popcornUserRepository.findAllByPopcornId(popcornId);
    }

    public List<PopcornUser> findAllByPopcornIdAndUserId(Long popcornId, Long userId) {
        return popcornUserRepository.findAllByPopcornIdAndUserId(popcornId,userId);
    }

    public List<PopcornUser> findAllByUserId(Long userId) {
        return popcornUserRepository.findAllByUserId(userId);
    }


    //1-30일부터 ㄱㄱ

    @Transactional
    public void postComplain(Long reviewId,Long userId) {
        PopcornUser popcornUser = findById(reviewId);

        blockAdaptor.save(userId,popcornUser.getUser().getId(),null,reviewId);

        int complainCount = popcornUser.getComplaintCount();
        if (complainCount == 4) {
            incrementComplaintCount(popcornUser);
            // Get user from the screeningReview
            User user = popcornUser.getUser();

            deActivateUser(user);

            // Delete the screeningReview
            changeBlindStatus(popcornUser);  // Assuming there is a method to delete screeningReview
        } else {
            incrementComplaintCount(popcornUser);
        }
    }


    private PopcornUser findById(Long reviewId) {
        return popcornUserRepository.findById(reviewId).get();
    }

    @Transactional
    public void deActivateUser(User user) {
        user.turnBlind();
    }

    @Transactional
    public void changeBlindStatus(PopcornUser popcornUser) {
        QPopcornUser qPopcornUser = QPopcornUser.popcornUser;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qPopcornUser);

//        // Set blindStatus to true
        updateClause
                .set(qPopcornUser.isBlind, true)
                .where(qPopcornUser.id.eq(popcornUser.getId()))
                .execute();

    }

    @Transactional
    public void incrementComplaintCount(PopcornUser popcornUser) {
        QPopcornUser qPopcornUser = QPopcornUser.popcornUser;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qPopcornUser);

//        // Set blindStatus to true
        updateClause
                .set(qPopcornUser.complaintCount, qPopcornUser.complaintCount.add(1))
                .where(qPopcornUser.id.eq(popcornUser.getId()))
                .execute();
    }
//    int total = popcornUserRepository.findAllByPopcornId(popcorn.getId()).size();
}
