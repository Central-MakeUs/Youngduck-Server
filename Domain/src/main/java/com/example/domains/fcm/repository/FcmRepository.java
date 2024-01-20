package com.example.domains.fcm.repository;

import com.example.domains.fcm.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmRepository extends JpaRepository<FcmToken, Long> {
}