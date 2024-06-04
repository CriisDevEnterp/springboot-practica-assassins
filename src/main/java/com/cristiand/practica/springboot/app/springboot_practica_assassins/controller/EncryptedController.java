package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encryption")
public class EncryptedController {

    @Autowired
    EncryptionService encryptionService;

    @PostMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestBody String data) {
        try {
            String encryptedData = encryptionService.encrypt(data);
            return ResponseEntity.ok(encryptedData);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while encrypting data: " + e.getMessage());
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<Object> decrypt(@RequestBody String data) {
        try {
            Object decryptedData = encryptionService.decrypt(data);
            return ResponseEntity.ok(decryptedData);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while decrypting data: " + e.getMessage());
        }
    }

}
