package lk.tech.moonminio.service;

import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import lk.tech.moonminio.dto.AtlasType;
import lk.tech.moonminio.dto.atlas.AtlasGettingResponse;
import lk.tech.moonminio.service.parent.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Service
public class MinioAtlasService extends MinioService {

    @Value("${minio.bucket-atlas}")
    private String bucketAtlas;

    public MinioAtlasService(MinioClient minioClient) {
        super(minioClient);
    }

    @PostConstruct
    public void init() {
        initBucket(bucketAtlas);
    }

    public AtlasGettingResponse getAtlas(String type) {
        AtlasType atlasType = AtlasType.fromName(type);
        InputStream file = getFile(type, bucketAtlas);
        String extension = atlasType.getExtension();

        AtlasGettingResponse response = new AtlasGettingResponse();
        response.setExtension(extension);
        response.setInputStream(file);

        return response;
    }

    public String uploadAtlas(MultipartFile tile, String type) {
        return uploadFile(tile, type, bucketAtlas);
    }
}
