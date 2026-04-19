package lk.tech.moonminio.dto.atlas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtlasGettingResponse {
    private InputStream inputStream;
    private String extension;
}
