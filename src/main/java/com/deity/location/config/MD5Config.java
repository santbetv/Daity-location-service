package com.deity.location.config;

import com.deity.location.common.exceptions.CustomNotFoundException;
import com.deity.location.strategyenum.StoragePathType;
import org.springframework.context.annotation.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MD5Config {


    public boolean isValidMd5(String md5Key, String imageName, String type) {
        String basePath = StoragePathType.fromCode(type).getPath();
        String filePath = basePath + imageName;
        String generatedMd5 = generateMd5(filePath);
        return generatedMd5.equals(md5Key);
    }

    private String generateMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new CustomNotFoundException("Error al generar el hash MD5" + e.getMessage());
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
