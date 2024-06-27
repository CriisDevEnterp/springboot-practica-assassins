package com.cristiand.practica.springboot.app.springboot_practica_assassins.util;

import java.io.FileWriter;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Clase para generar una clave AES y un vector de inicialización (IV), y
 * guardarlos en application.properties.
 */
public class AESKeyGenerator {

    /**
     * Método principal para generar y guardar la clave AES y el IV.
     */
    public static void main(String[] args) {
        try {
            // Generar clave secreta AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // Puedes cambiar esto a 192 o 256 si tu entorno lo soporta
            SecretKey secretKey = keyGen.generateKey();
            String encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            // Generar vector de inicialización (IV)
            byte[] iv = new byte[16]; // AES usa un IV de 16 bytes
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            String encodedIv = Base64.getEncoder().encodeToString(iv);

            // Escribir clave y IV en application.properties
            Properties properties = new Properties();
            properties.setProperty("aes.secret.key", encodedSecretKey);
            properties.setProperty("aes.init.vector", encodedIv);

            try (FileWriter writer = new FileWriter("src/main/resources/application.properties", true)) {
                properties.store(writer, "AES Key and IV");
            }

            System.out.println("AES Key and IV generated and saved to application.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
