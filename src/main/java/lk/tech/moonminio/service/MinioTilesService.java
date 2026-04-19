package lk.tech.moonminio.service;

import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import lk.tech.moonminio.service.parent.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Service
public class MinioTilesService extends MinioService {

    @Value("${minio.bucket-tile}")
    private String bucketTile;

    public MinioTilesService(MinioClient minioClient) {
        super(minioClient);
    }

    @PostConstruct
    public void init() {
        initBucket(bucketTile);
    }

    public InputStream getTile(String tileId) {
        return getFile(tileId, bucketTile);
    }

    public String uploadTile(MultipartFile tile) {
        return uploadFile(tile, bucketTile);
    }

}
