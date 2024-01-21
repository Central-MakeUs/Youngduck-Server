package com.example.fcm.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class FCMInitializer {
    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        ClassPathResource resource = new ClassPathResource(firebaseConfigPath);
        InputStream resourceInputStream = resource.getInputStream();
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> apps = FirebaseApp.getApps();

        if (apps != null && !apps.isEmpty()) {
            for (FirebaseApp app : apps) {
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                    firebaseApp = app;
                }
            }
        }
        else {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(resourceInputStream)).build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }
        return FirebaseMessaging.getInstance(firebaseApp);
    }

//    @PostConstruct
//    private void initialize() {
//        try {
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseOptions options =
//                        FirebaseOptions.builder()
//                                .setCredentials(
//                                        GoogleCredentials.fromStream(
//                                                new ByteArrayInputStream(
//                                                        firebaseConfigPath.getBytes(StandardCharsets.UTF_8))))
//                                .build();
//                FirebaseApp.initializeApp(options);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

