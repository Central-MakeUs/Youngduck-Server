package com.example.domains.popcornUser.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.popcorn.entity.Popcorn;
import com.example.domains.popcorn.entity.QPopcorn;
import com.example.domains.popcornUser.entity.PopcornUser;
import com.example.domains.popcornUser.repository.PopcornUserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class PopcornUserAdaptor {
    private final PopcornUserRepository popcornUserRepository;
    private final JPAQueryFactory jpaQueryFactory;
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
//    int total = popcornUserRepository.findAllByPopcornId(popcorn.getId()).size();
}
