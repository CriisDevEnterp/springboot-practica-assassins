package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.*;

/**
 * Marca esta clase como un controlador REST que maneja las operaciones
 * relacionadas con datos.
 */
@RestController
/**
 * Define la raíz del mapeo para todas las solicitudes en este controlador.
 * Todas las rutas definidas en este controlador serán relativas a /encryption.
 */
@RequestMapping("/encryption")
public class EncryptionController {

    /**
     * Inyección del servicio EncryptionService para manejar operaciones
     * relacionadas a la encriptación de datos.
     */
    @Autowired
    EncryptionService encryptionService;

    @PostMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestBody(required = false) String data) {
        // Llama al servicio encrypt para realizar la encriptación de datos.
        String encryptedData = encryptionService.encrypt(data);

        // Retorna una respuesta con la información encriptada y el código de estado
        // HTTP 200 (OK).
        return new ResponseEntity<>(encryptedData, OK);
    }

    @PostMapping("/decrypt")
    public ResponseEntity<Object> decrypt(@RequestBody(required = false) String data) {
        // Llama al servicio decrypt para realizar la desencriptación.
        Object decryptedData = encryptionService.decrypt(data);

        // Retorna una respuesta con la información desencriptada y el código de estado
        // HTTP 200 (OK).
        return new ResponseEntity<>(decryptedData, OK);
    }

}
