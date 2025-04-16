package com.deity.location.config;

import com.deity.location.domain.record.StorageDTO;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Configuration
public class BucketConfig {

    @Value("${google.cloud.bucketName}")
    private String googleCloudBucketName;

    @Value("${google.cloud.projectId}")
    private String googleCloudProjectId;

    @Value("${google.cloud.credentials.path}")
    private String credentialString;

    @Bean
    public StorageDTO storageDTO() throws IOException {
        GoogleCredentials credentials = decodeCredentials(credentialString);
        return new StorageDTO(googleCloudBucketName, googleCloudProjectId, credentials);
    }

    private GoogleCredentials decodeCredentials(String base64Credentials) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        ByteArrayInputStream credentialsStream = new ByteArrayInputStream(decodedBytes);
        return GoogleCredentials.fromStream(credentialsStream);
    }
}
