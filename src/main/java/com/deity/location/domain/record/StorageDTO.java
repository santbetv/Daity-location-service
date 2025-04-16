package com.deity.location.domain.record;

import com.google.auth.oauth2.GoogleCredentials;

public record StorageDTO(
        String googleCloudBucketName,
        String googleCloudProjectId,
        GoogleCredentials googleCloudCredentialsPath
) {
}