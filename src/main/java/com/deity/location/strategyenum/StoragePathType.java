package com.deity.location.strategyenum;

import com.deity.location.common.exceptions.CustomNotFoundException;

import java.util.Arrays;

public enum StoragePathType {
    BUSINESS("B", "daity-business/users/photo/"),
    USERS("U", "daity-user/users/photo/");

    private final String code;
    private final String path;

    StoragePathType(String code, String path) {
        this.code = code;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static StoragePathType fromCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new CustomNotFoundException(
                        "Tipo de ruta inválido. Debe ser 'B' (business) o 'U' (users)."));
    }
}

