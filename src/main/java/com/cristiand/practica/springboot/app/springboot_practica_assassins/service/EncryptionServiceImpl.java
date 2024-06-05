package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private final String secretKey;
    private final String initVector;

    public EncryptionServiceImpl(@Value("${aes.secret.key}") String secretKey,
                                 @Value("${aes.init.vector}") String initVector) {
        this.secretKey = secretKey;
        this.initVector = initVector;
    }

    @Override
    public String encrypt(String decryptedData) {
        if (decryptedData == null) {
            throw new IllegalArgumentException("Error while encrypting data: input data is null");
        }
        try {
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(initVector));
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(decryptedData.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            throw new RuntimeException("Error while encrypting data", ex);
        }
    }

    @Override
    public Object decrypt(String encryptedData) {
        if (encryptedData == null) {
            throw new IllegalArgumentException("Error while decrypting data: input data is null");
        }

        // Elimina las comillas dobles al inicio y al final de la cadena
        encryptedData = encryptedData.replaceAll("^\"|\"$", "");

        try {
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(initVector));
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(original);
        } catch (Exception ex) {
            throw new RuntimeException("Error while decrypting data", ex);
        }
    }
}
