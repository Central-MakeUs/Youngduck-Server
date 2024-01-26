package com.example.fcm.repository;

import com.example.fcm.entity.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmRepository extends JpaRepository<FCMToken, Long> {
}