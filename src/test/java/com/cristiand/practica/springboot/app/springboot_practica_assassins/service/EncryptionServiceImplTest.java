package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;

public class EncryptionServiceImplTest {

    private EncryptionService encryptionService;

    @BeforeEach
    public void setUp() {
        encryptionService = new EncryptionServiceImpl("xuMxx0/UhtuQkt0IFzmDJw==", "qd6m3gEIJOYeKpW+sTkHeQ==");
    }

    @Test
    public void testEncryptDecrypt() {
        String originalData = "Hello, World!";
        String encryptedData = encryptionService.encrypt(originalData);
        String decryptedData = (String) encryptionService.decrypt(encryptedData);

        assertEquals(originalData, decryptedData);
    }

    @Test
    public void testEncryptNullData() {
        assertThrows(CustomAssassinException.class, () -> {
            encryptionService.encrypt(null);
        });
    }

    @Test
    public void testDecryptNullData() {
        assertThrows(CustomAssassinException.class, () -> {
            encryptionService.decrypt(null);
        });
    }

    @Test
    public void testDecryptInvalidData() {
        assertThrows(RuntimeException.class, () -> {
            encryptionService.decrypt("invalidData");
        });
    }
    
}
