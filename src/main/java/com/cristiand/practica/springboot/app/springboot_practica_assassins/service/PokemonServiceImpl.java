package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.ReadPokemonDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PokemonServiceImpl implements PokemonService {

    private static final String POKEMON_NOT_FOUND_MESSAGE = "Pokemon not found";
    private static final String FETCH_ERROR_MESSAGE = "An error occurred while fetching the Pokemon info";
    private static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred";

    private String pokemonApiUrl;

    private final RestTemplate restTemplate;

    public PokemonServiceImpl(@Value("${pokemon.api.url}") String pokemonApiUrl, RestTemplate restTemplate) {
        this.pokemonApiUrl = pokemonApiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public ReadPokemonDto getPokemonInfo(String pokemonName) {
        String url = pokemonApiUrl + pokemonName.toLowerCase();

        try {
            // Realizar la llamada a la API externa
            return restTemplate.getForObject(url, ReadPokemonDto.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, POKEMON_NOT_FOUND_MESSAGE);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, FETCH_ERROR_MESSAGE, e);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR_MESSAGE, e);
        }
    }
    
}
