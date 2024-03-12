package com.example.domains.kafka.notifications.repository;

//Jpa로 바꾸기

import com.example.domains.kafka.notifications.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notifications,Long> {

}