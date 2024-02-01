package com.example.api.screening.service;

import com.example.ApiApplication;
import com.example.CoreApplication;
import com.example.DomainApplication;
import com.example.InfraApplication;
import com.example.api.config.SecurityConfig;
import com.example.api.screening.controller.ScreeningController;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.HostInfo;
import com.example.domains.screening.repository.ScreeningRepository;
import com.example.domains.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.util.Random;

import static com.example.domains.screening.enums.Category.GRADUATE;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        properties = "spring.profiles.active=local"
)
@TestPropertySource(locations = {"classpath:application.yml"})
@Transactional
@ContextConfiguration(classes = {ApiApplication.class, InfraApplication.class,DomainApplication.class,CoreApplication.class})
class ScreeningUploadUseCaseTest {

    @Autowired
    private ScreeningAdaptor screeningAdaptor;

    @Test
    void upload(){
        final HostInfo hostInfo = HostInfo.of(
                "이한비",
                "010-0000-0000",
                "bee116@naver.com"
        );
        final Screening newScreening = Screening.of(
                "ScreeningTitle",
                "request.getPosterImgUrl()",
                hostInfo,
                "request.getLocation()",
                "request.getFormUrl()",
                "request.getInformation()",
    LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                GRADUATE
        );

        //When
// Stubbing: When screeningAdaptor.save is called with newScreening, then return newScreening
        Screening savedScreening = screeningAdaptor.save(newScreening);

        System.out.println(savedScreening.getId());
        // Then
        Assertions.assertThat(savedScreening).isSameAs(newScreening);

    }


}