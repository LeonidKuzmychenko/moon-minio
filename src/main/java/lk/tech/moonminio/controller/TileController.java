package lk.tech.moonminio.controller;

import lk.tech.moonminio.dto.tile.TileDto;
import lk.tech.moonminio.service.MinioTilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("api/v1/tile")
@RequiredArgsConstructor
public class TileController {

    private final MinioTilesService minioTilesService;

    @PostMapping
    public ResponseEntity<TileDto> uploadTile(@RequestParam("file") MultipartFile tile) {
        String uuid = minioTilesService.uploadTile(tile);
        TileDto tileDto = new TileDto(uuid);
        return ResponseEntity.ok(tileDto);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> getTile(@RequestParam("tileId") String tileId) {
        InputStream inputStream = minioTilesService.getTile(tileId);
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // or detect from MinIO metadata
                .body(new InputStreamResource(inputStream));
    }
}
