package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.ReadPokemonDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.enums.ErrorCode;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PokemonServiceImpl implements PokemonService {

    private String pokemonApiUrl;
    private final RestTemplate restTemplate;

    /**
     * Constructor de la clase PokemonServiceImpl.
     *
     * @param pokemonApiUrl URL base para la API de Pokémon.
     * @param restTemplate  RestTemplate para realizar llamadas HTTP.
     */
    public PokemonServiceImpl(@Value("${pokemon.api.url}") String pokemonApiUrl, RestTemplate restTemplate) {
        this.pokemonApiUrl = pokemonApiUrl;
        this.restTemplate = restTemplate;
    }

    /**
     * Obtiene la información de un Pokémon mediante su nombre.
     *
     * @param pokemonName Nombre del Pokémon a buscar.
     * @return Objeto ReadPokemonDto que contiene la información del Pokémon.
     * @throws CustomAssassinException Si el Pokémon no se encuentra o se produce un
     *                                 error inesperado.
     * @throws Exception               Si ocurre un error inesperado durante la
     *                                 obtención de la información del Pokémon.
     */
    @Override
    public ReadPokemonDto getPokemonInfo(String pokemonName) throws CustomAssassinException, Exception {
        String url = pokemonApiUrl + pokemonName.toLowerCase();

        try {
            // Realizar la llamada a la API externa
            return restTemplate.getForObject(url, ReadPokemonDto.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomAssassinException("Pokémon no encontrado.", e, ErrorCode.NOT_FOUND);
            } else {
                throw new CustomAssassinException("Se produjo un error al obtener la información del Pokémon.", e,
                        ErrorCode.INTERNAL_SERVER_ERROR);
            }
        } catch (CustomAssassinException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Se produjo un error inesperado.", e);
        }
    }

}
