package com.example.oauth.apple.config;

import com.amazonaws.util.IOUtils;
import com.example.error.BaseRunTimeException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;

import java.io.InputStream;

public class AppleOAuthErrorDecoder implements ErrorDecoder {
    @Override
    @SneakyThrows
    public Exception decode(String methodKey, Response response) {
        InputStream inputStream = response.body().asInputStream();
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        String responseBody = new String(byteArray);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String error = jsonNode.get("error") == null ? null : jsonNode.get("error").asText();
        String errorDescription =
                jsonNode.get("error_description") == null
                        ? null
                        : jsonNode.get("error_description").asText();

        System.out.println(jsonNode);
        throw new BaseRunTimeException(response.status(), error, errorDescription);
    }
}
