package lk.tech.moonminio.controller;

import lk.tech.moonminio.dto.atlas.AtlasGettingResponse;
import lk.tech.moonminio.dto.atlas.AtlasUploadResponse;
import lk.tech.moonminio.service.MinioAtlasService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("api/v1/atlas")
@RequiredArgsConstructor
public class AtlasController {

    private final MinioAtlasService minioAtlasService;

    @PostMapping
    public ResponseEntity<AtlasUploadResponse> uploadAtlas(@RequestParam("file") MultipartFile atlas,
                                                           @RequestParam("type") String type) {
        String atlasName = minioAtlasService.uploadAtlas(atlas, type);
        AtlasUploadResponse atlasUploadResponse = new AtlasUploadResponse(atlasName);
        return ResponseEntity.ok(atlasUploadResponse);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> getAtlas(@RequestParam("atlasType") String type) {
        AtlasGettingResponse response = minioAtlasService.getAtlas(type);
        InputStream inputStream = response.getInputStream();
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/" + response.getExtension()))
                .body(new InputStreamResource(inputStream));
    }
}
