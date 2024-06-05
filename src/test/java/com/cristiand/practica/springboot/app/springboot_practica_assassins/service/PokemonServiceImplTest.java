package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.ReadPokemonDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.SpeciesDto;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PokemonServiceImplTest {

    private PokemonServiceImpl pokemonService;
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        pokemonService = new PokemonServiceImpl("https://pokeapi.co/api/v2/pokemon/", restTemplate);
    }

    @Test
    public void testGetPokemonInfoSuccess() {
        String pokemonName = "pikachu";

        ReadPokemonDto actualPokemon = pokemonService.getPokemonInfo(pokemonName);

        // Verificar que el objeto no sea nulo
        assertNotNull(actualPokemon);

        // Verificar los tipos de datos esperados
        assertEquals(Integer.class, Integer.valueOf(actualPokemon.id()).getClass());
        assertEquals(Boolean.class, actualPokemon.is_default().getClass());
        assertEquals(String.class, actualPokemon.location_area_encounters().getClass());
        assertEquals(ArrayList.class, actualPokemon.moves().getClass());
        assertEquals(String.class, actualPokemon.name().getClass());
        assertEquals(Integer.class, Integer.valueOf(actualPokemon.order()).getClass());
        assertEquals(ArrayList.class, actualPokemon.past_abilities().getClass());
        assertEquals(ArrayList.class, actualPokemon.past_types().getClass());
        assertEquals(SpeciesDto.class, actualPokemon.species().getClass());
        assertEquals(LinkedHashMap.class, actualPokemon.sprites().getClass());
        assertEquals(ArrayList.class, actualPokemon.stats().getClass());
        assertEquals(ArrayList.class, actualPokemon.types().getClass());
        assertEquals(Integer.class, Integer.valueOf(actualPokemon.weight()).getClass());
    }

    @Test
    public void testGetPokemonInfoNotFound() {
        String pokemonName = "unknownPokemon";

        // Simular la excepción al llamar al método getForObject
        assertThrows(ResponseStatusException.class, () -> {
            pokemonService.getPokemonInfo(pokemonName);
        });
    }

    @Test
    public void testGetPokemonInfoOtherError() {
        String pokemonName = "errorPokemon";

        // Simular la excepción al llamar al método getForObject
        assertThrows(ResponseStatusException.class, () -> {
            pokemonService.getPokemonInfo(pokemonName);
        });
    }
    
}

