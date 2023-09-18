package com.example.docs.global.utils;

import lombok.Data;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
public class AESCryptoUtil {
    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;
    private String specName;

    public AESCryptoUtil() throws Exception {
        this.secretKey = getKey();
        this.ivParameterSpec = getIv();
        this.specName = "AES/CBC/PKCS5Padding";
    }

    public String encrypt(
            String plainText
    ) throws Exception {
        Cipher cipher = Cipher.getInstance(this.specName);
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.ivParameterSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(encrypted));
    }

    public String decrypt(
            String cipherText
    ) throws Exception {
        Cipher cipher = Cipher.getInstance(this.specName);
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.ivParameterSpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    // 암호화 키
    public static SecretKey getKey() throws Exception {
        String key = "TruthSkyNetWillBeAllOverTheWorld";
        try {
            SecretKey secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            return secretKey;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 초기화 벡터
    public static IvParameterSpec getIv() {
        return new IvParameterSpec(new byte[]{-2, 127, 77, -89, 118, -43, 33, 69, 21, 109, -113, 1, 70, 73, 116, 93});
    }

    // 암호화
    public static String encrypt(
        String specName,
        SecretKey key,
        IvParameterSpec iv,
        String plainText
    ) throws Exception {
        Cipher cipher = Cipher.getInstance(specName);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(encrypted));
    }

    public static String decrypt(
        String specName,
        SecretKey key,
        IvParameterSpec iv,
        String cipherText
    ) throws Exception {
        Cipher cipher = Cipher.getInstance(specName);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
