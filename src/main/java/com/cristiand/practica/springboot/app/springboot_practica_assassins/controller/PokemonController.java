package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.ReadPokemonDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.*;

/**
 * Marca esta clase como un controlador REST que maneja las operaciones
 * relacionadas con los pokemons.
 */
@RestController
/**
 * Define la raíz del mapeo para todas las solicitudes en este controlador.
 * Todas las rutas definidas en este controlador serán relativas a
 * /api/pokemons.
 */
@RequestMapping("/api/pokemons")
public class PokemonController {

    /**
     * Inyección del servicio PokemonService para manejar operaciones relacionadas
     * con pokemons.
     */
    @Autowired
    PokemonService pokemonService;

    @GetMapping("/{name}")
    public ResponseEntity<ReadPokemonDto> getPokemonInfo(@PathVariable String name)
            throws CustomAssassinException, Exception {
        // Llama al servicio getPokemonInfo para obtener la información del Pokemon a
        // buscar.
        ReadPokemonDto readPokemonDto = pokemonService.getPokemonInfo(name);
        // Retorna una respuesta con la información del pokemon y el código de estado
        // HTTP 200 (OK).
        return new ResponseEntity<>(readPokemonDto, OK);
    }

}
