package lk.tech.moonminio.controller;

import lk.tech.moonminio.dto.TileDto;
import lk.tech.moonminio.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/tile")
@RequiredArgsConstructor
public class TileController {

    private final MinioService minioService;

    @PostMapping
    public ResponseEntity<TileDto> uploadTile(@RequestParam("file") MultipartFile file) {
        String uuid = minioService.uploadTile(file);
        TileDto tileDto = new TileDto(uuid);
        return ResponseEntity.ok(tileDto);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> getTile(@RequestParam("tileId") String uuid) {
        InputStream inputStream = minioService.getTile(uuid);
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // or detect from MinIO metadata
                .body(new InputStreamResource(inputStream));
    }
}
