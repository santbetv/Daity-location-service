package com.deity.location.service;

import com.deity.location.common.exceptions.CustomNotFoundException;
import com.deity.location.domain.record.StorageDTO;
import com.deity.location.strategyenum.StoragePathType;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GCPStorageService {

    private final StorageDTO storageDTO;
    private final Storage storage;

    @Autowired
    public GCPStorageService(StorageDTO storageDTO) {
        this.storageDTO = storageDTO;

        this.storage = StorageOptions.newBuilder()
                .setProjectId(storageDTO.googleCloudProjectId())
                .setCredentials(storageDTO.googleCloudCredentialsPath())
                .build()
                .getService();
        verifyConnection(storage, storageDTO.googleCloudBucketName());
    }

    public Mono<String> uploadFile(FilePart filePart, String customFileName, String type) {
        String basePath = StoragePathType.fromCode(type).getPath();
        String filePath = basePath + customFileName;
        BlobId blobId = BlobId.of(storageDTO.googleCloudBucketName(), filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        return DataBufferUtils.join(filePart.content())
                .flatMap(dataBuffer -> Mono.fromCallable(() -> {
                    try (WritableByteChannel channel = storage.writer(blobInfo)) {
                        ByteBuffer byteBuffer = dataBuffer.asByteBuffer();
                        while (byteBuffer.hasRemaining()) {
                            channel.write(byteBuffer);
                        }
                    } finally {
                        DataBufferUtils.release(dataBuffer);
                    }
                    return filePath;
                }));
    }

    public byte[] downloadFile(String imageName, String type) {
        String basePath = StoragePathType.fromCode(type).getPath();
        String filePath = basePath + imageName;
        BlobId blobId = BlobId.of(storageDTO.googleCloudBucketName(), filePath);
        Blob blob = storage.get(blobId);
        if (blob == null) {
            throw new CustomNotFoundException("El archivo no existe: " + filePath);
        }
        return blob.getContent();
    }

    private void verifyConnection(Storage storage, String bucketName) {
        Optional.ofNullable(storage.get(bucketName))
                .map(bucket -> {
                    List<Blob> blobs = bucket.list().streamAll().collect(Collectors.toList());
                    if (blobs.isEmpty()) {
                        log.info("El bucket está vacío o no tiene objetos accesibles.: {}", 0);
                    } else {
                        log.info("Conexión exitosa. Objetos en el bucket.: {}", blobs.size());
                    }
                    return blobs; // Se devuelve la lista de blobs, aunque no es necesario en este caso
                })
                .orElseThrow(() -> {
                    String errorMessage = "No se pudo conectar al bucket: " + bucketName;
                    log.error(errorMessage);
                    return new RuntimeException(errorMessage);
                });
    }
}
