package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Value("${aes.secret.key}")
    private String secretKey;

    @Value("${aes.init.vector}")
    private String initVector;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String encrypt(String decryptedData) {
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
        // Elimina las comillas dobles al inicio y al final de la cadena
        encryptedData = encryptedData.replaceAll("^\"|\"$", "");
        
        try {
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(initVector));
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return objectMapper.readValue(new String(original), Object.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error while decrypting data", ex.getCause());
        }
    }

}
