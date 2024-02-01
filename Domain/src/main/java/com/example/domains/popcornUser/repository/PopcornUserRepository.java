package com.example.domains.popcornUser.repository;

import com.example.domains.popcornUser.entity.PopcornUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PopcornUserRepository extends JpaRepository<PopcornUser, Long> {
    List<PopcornUser> findAllByPopcornId(Long id);

    List<PopcornUser> findAllByPopcornIdAndUserId(Long popcornId, Long userId);

    List<PopcornUser> findAllByUserId(Long userId);

    Optional<PopcornUser> findById(Long id);
    // 특정 popcornId에 해당하는 popcornUser 개수 조회
    int countByPopcornId(Long popcornId);
    // 특정 popcornId에 해당하는 distinct한 userId 개수 조회
    @Query("SELECT COUNT(DISTINCT p.user.id) FROM PopcornUser p WHERE p.popcorn.id = :popcornId")
    int countDistinctUserIdByPopcornId(@Param("popcornId") Long popcornId);

    boolean existsByUserIdAndPopcornId(Long userId, Long popcornId);
}
