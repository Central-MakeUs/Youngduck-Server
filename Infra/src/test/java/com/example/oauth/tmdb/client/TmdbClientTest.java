package com.example.oauth.tmdb.client;

import com.example.config.InfraIntegrateProfileResolver;
import com.example.config.InfraIntegrateTestConfig;
import com.example.oauth.tmdb.dto.TdmbResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import feign.Response;
import net.minidev.json.JSONObject;
import okhttp3.ResponseBody;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest(classes = InfraIntegrateTestConfig.class)
@AutoConfigureWireMock(port=0)
@ActiveProfiles(resolver = InfraIntegrateProfileResolver.class)
@TestPropertySource(properties = { "spring.thymeleaf.enabled=false","test.port=http://localhost:${wiremock.server.port}"})
public class TmdbClientTest {

        @Autowired
        private TmdbClient tmdbClient;
        @Test
        public void testGetMovieData() throws IOException, ParseException {
                Path file = ResourceUtils.getFile("classpath:pamyo-response.json").toPath();
                // WireMock 설정
                stubFor(get(urlEqualTo("/openapi-data2/wisenut/search_api/search_xml2.jsp"))
                        .withQueryParam("collection", equalTo("kmdb_new2"))
                        .withQueryParam("movieId", equalTo("K"))
                        .withQueryParam("movieSeq", equalTo("35655"))
                        .withQueryParam("detail", equalTo("Y"))
                        .withQueryParam("ServiceKey", equalTo(""))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBody(Files.readAllBytes(file))));

                // 테스트할 Feign Client 메소드 호출
                try {

                        String mmovieData = tmdbClient.getMovieData("K", "35655", "Y","a");
                        System.out.println(mmovieData);

                        JsonParser jsonParser = new JsonParser();

                        //3. To Object

                        //값 처리하는 로직
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode rootNode = objectMapper.readTree(mmovieData);
                        //4. To JsonObject

                        JsonNode movieData = rootNode.path("Data").path(0).path("Result").path(0);


                        String title = movieData.path("title").asText();
                        title = title.trim();
                        String directorNm = movieData.path("directors").path("director").path(0).path("directorNm").asText();
                        String plotText = movieData.path("plots").path("plot").path(0).path("plotText").asText();
                        String firstPosterUrl = movieData.path("posters").asText().split("\\|")[0];
                        //System.out.println(tes);

                        System.out.println(title +directorNm +plotText+ firstPosterUrl);
                        // 응답이 예상대로 받아졌는지 검증합니다.
                        //assertNotNull("check",tes);
                }catch (Exception e) {
                        System.out.println(e.getMessage());
                }
                //assertEquals("right",test.size(),1);

        }
}