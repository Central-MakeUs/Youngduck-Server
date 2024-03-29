package com.example.domains.userscreening.repository;

import com.example.domains.userscreening.entity.UserScreening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserScreeningRepository extends JpaRepository<UserScreening, Long> {

    Optional<UserScreening> findByUserIdAndScreeningId(Long userId, Long screeningId);

    List<UserScreening> findByIsBookmarked(boolean b);

    List<UserScreening> findByScreeningId(Long screeningId);

    boolean existsByUserIdAndScreeningId(Long userId, Long screeningId);
}
