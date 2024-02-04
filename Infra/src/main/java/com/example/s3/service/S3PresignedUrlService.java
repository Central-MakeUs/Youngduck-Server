package com.example.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3PresignedUrlService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final AmazonS3Client amazonS3Client;


    /* 1. 파일 업로드 */
    public String upload(MultipartFile multipartFile) {
        // 메타데이터 설정
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        // 실제 S3 bucket 디렉토리명 설정
        // 파일명 중복을 방지하기 위한 UUID 추가
        String fileName = bucket + "/" + UUID.randomUUID() + "." + multipartFile.getOriginalFilename();

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
        } catch (IOException e) {
            log.error("S3 파일 업로드에 실패했습니다. {}", e.getMessage());
            throw new IllegalStateException("S3 파일 업로드에 실패했습니다.");
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    /* 2. 파일 삭제 */
    public void delete (String keyName) {
        try {
            // deleteObject(버킷명, 키값)으로 객체 삭제
            amazonS3.deleteObject(bucket, keyName);
        } catch (AmazonServiceException e) {
            log.error(e.toString());
        }
    }

    /* 3. 파일의 presigned URL 반환 */
    public String getPreSignedUrl(String prefix, String fileName) {
        if (!prefix.isEmpty()) {
            fileName = createPath(prefix, fileName);
        }
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(fileName);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    private String createFileId() {
          return UUID.randomUUID().toString();
    }
    private String createPath(String prefix, String fileName) {
        String fileId = createFileId();
        return String.format("%s/%s", prefix, fileId + "-" + fileName);
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        log.info(expiration.toString());
        return expiration;
    }
}