package lk.tech.moonminio.service.parent;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class MinioService {

    protected final MinioClient minioClient;

    protected void initBucket(String bucketName) {
        try {
            boolean bucketTilesExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketTilesExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("Bucket '{}' created successfully", bucketName);
            }
        } catch (Exception e) {
            log.error("Error during bucket initialization", e);
        }
    }

    protected String uploadFile(MultipartFile file, String bucketName) {
        String uuid = UUID.randomUUID().toString();
        return uploadFile(file, uuid, bucketName);
    }

    protected String uploadFile(MultipartFile file, String fileName, String bucketName) {
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return fileName;
        } catch (Exception e) {
            log.error("Error uploading file to MinIO", e);
            throw new RuntimeException("Failed to upload file to MinIO", e);
        }
    }

    protected InputStream getFile(String id, String bucketName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(id)
                            .build()
            );
        } catch (Exception e) {
            log.error("Error retrieving file from MinIO: {}", id, e);
            return null;
        }
    }
}
