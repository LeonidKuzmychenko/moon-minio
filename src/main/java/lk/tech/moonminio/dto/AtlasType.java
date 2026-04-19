package lk.tech.moonminio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

//ATLAS,MOON,RESULT,KTX2_POOR,KTX2_MEDIUM,KTX2_EXCELLENT
@Getter
@AllArgsConstructor
public enum AtlasType {

    ATLAS("png"),
    MOON("jpeg"),
    RESULT("jpeg"),

    KTX2_POOR("krx2"),
    KTX2_MEDIUM("krx2"),
    KTX2_EXCELLENT("krx2");

    private final String extension;

    public static AtlasType fromName(String name) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unknown AtlasType: " + name
                        )
                );
    }
}