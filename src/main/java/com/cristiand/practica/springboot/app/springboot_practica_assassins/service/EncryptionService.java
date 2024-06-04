package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

public interface EncryptionService {
    
    String encrypt(String decryptedData);

    Object decrypt(String encryptedData);
    
}
