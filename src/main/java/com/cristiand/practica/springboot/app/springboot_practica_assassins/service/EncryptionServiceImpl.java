package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.enums.ErrorCode;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private final String secretKey;
    private final String initVector;

    /**
     * Constructor que inicializa la clave secreta y el vector de inicialización.
     *
     * @param secretKey  Clave secreta para el cifrado AES.
     * @param initVector Vector de inicialización para el cifrado AES.
     */
    public EncryptionServiceImpl(@Value("${aes.secret.key}") String secretKey,
            @Value("${aes.init.vector}") String initVector) {
        this.secretKey = secretKey;
        this.initVector = initVector;
    }

    /**
     * Cifra los datos proporcionados utilizando AES en modo CBC con relleno PKCS5.
     *
     * @param decryptedData Datos a cifrar.
     * @return Datos cifrados en formato Base64.
     * @throws CustomAssassinException si los datos de entrada son nulos o si ocurre
     *                                 un error durante el cifrado.
     */
    @Override
    public String encrypt(String decryptedData) throws IllegalArgumentException, CustomAssassinException {
        try {
            // Verifica si los datos de entrada son nulos.
            if (decryptedData == null) {
                throw new IllegalArgumentException("Error al cifrar datos: los datos de entrada son nulos.");
            }

            // Inicializa el vector de inicialización y la clave secreta.
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(initVector));
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "AES");

            // Configura el cifrado AES en modo CBC con relleno PKCS5.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            // Cifra los datos y los convierte a Base64.
            byte[] encrypted = cipher.doFinal(decryptedData.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (IllegalArgumentException ex) {
            // Captura y relanza IllegalArgumentException como CustomAssassinException con
            // código de error INVALID_REQUEST.
            throw new CustomAssassinException(ex.getMessage(), ErrorCode.INVALID_REQUEST);
        } catch (Exception ex) {
            // Captura cualquier otra excepción y la relanza como CustomAssassinException
            // con mensaje personalizado y código de error INTERNAL_SERVER_ERROR.
            throw new CustomAssassinException("Error al cifrar datos", ex, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Descifra los datos cifrados en formato Base64 utilizando AES en modo CBC con
     * relleno PKCS5.
     *
     * @param encryptedData Datos cifrados en formato Base64.
     * @return Datos descifrados como una cadena de caracteres.
     * @throws CustomAssassinException si los datos de entrada son nulos, no están
     *                                 en el formato esperado, o si ocurre un error
     *                                 durante el descifrado.
     */
    @Override
    public Object decrypt(String encryptedData) throws IllegalArgumentException, CustomAssassinException {
        try {
            // Verifica si los datos de entrada son nulos.
            if (encryptedData == null) {
                throw new IllegalArgumentException("Error al descifrar datos: los datos de entrada son nulos.");
            }

            // Elimina las comillas dobles al inicio y al final de la cadena, si están
            // presentes.
            encryptedData = encryptedData.replaceAll("^\"|\"$", "");

            // Inicializa el vector de inicialización y la clave secreta.
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(initVector));
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "AES");

            // Configura el cifrado AES en modo CBC con relleno PKCS5 para descifrar.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            // Descifra los datos cifrados en Base64 y los convierte de nuevo a una cadena
            // de caracteres.
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(original);
        } catch (IllegalArgumentException ex) {
            // Captura y relanza IllegalArgumentException como CustomAssassinException con
            // código de error INVALID_REQUEST.
            throw new CustomAssassinException(ex.getMessage(), ErrorCode.INVALID_REQUEST);
        } catch (Exception ex) {
            // Captura cualquier otra excepción y la relanza como CustomAssassinException
            // con mensaje personalizado y código de error INTERNAL_SERVER_ERROR.
            throw new CustomAssassinException("Error al descifrar datos", ex, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
