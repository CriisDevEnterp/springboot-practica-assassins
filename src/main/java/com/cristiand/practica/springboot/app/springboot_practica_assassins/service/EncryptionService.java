package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;

/**
 * Interfaz que define métodos para cifrar y descifrar datos utilizando AES.
 */
public interface EncryptionService {

    /**
     * Cifra los datos proporcionados utilizando AES en modo CBC con relleno PKCS5.
     *
     * @param decryptedData Datos a cifrar.
     * @return Datos cifrados en formato Base64.
     * @throws IllegalArgumentException si los datos de entrada son nulos.
     * @throws CustomAssassinException  si ocurre un error durante el cifrado.
     */
    String encrypt(String decryptedData) throws IllegalArgumentException, CustomAssassinException;

    /**
     * Descifra los datos cifrados en formato Base64 utilizando AES en modo CBC con
     * relleno PKCS5.
     *
     * @param encryptedData Datos cifrados en formato Base64.
     * @return Datos descifrados como una cadena de caracteres.
     * @throws IllegalArgumentException si los datos de entrada son nulos.
     * @throws CustomAssassinException  si los datos de entrada no están en el
     *                                  formato esperado o si ocurre un error
     *                                  durante el descifrado.
     */
    Object decrypt(String encryptedData) throws IllegalArgumentException, CustomAssassinException;

}
