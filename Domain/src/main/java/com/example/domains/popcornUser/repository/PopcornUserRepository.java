package com.example.domains.popcornUser.repository;

import com.example.domains.popcornUser.entity.PopcornUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PopcornUserRepository extends JpaRepository<PopcornUser, Long> {
    List<PopcornUser> findAllByPopcornId(Long id);

    List<PopcornUser> findAllByPopcornIdAndUserId(Long popcornId, Long userId);

    List<PopcornUser> findAllByUserId(Long userId);
}
