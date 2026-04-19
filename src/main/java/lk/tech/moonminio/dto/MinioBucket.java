package lk.tech.moonminio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MinioBucket {

    TILE("tile"), ATLAS("atlas");

    private final String bucketName;
}