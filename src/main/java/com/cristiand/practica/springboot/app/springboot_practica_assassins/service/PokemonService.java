package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.ReadPokemonDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;

/**
 * Interfaz que define métodos para obtener información de Pokémon.
 */
public interface PokemonService {

    /**
     * Obtiene la información de un Pokémon mediante su nombre.
     *
     * @param pokemonName Nombre del Pokémon a buscar.
     * @return Objeto ReadPokemonDto que contiene la información del Pokémon.
     * @throws CustomAssassinException Si el Pokémon no se encuentra.
     * @throws Exception               Si ocurre un error inesperado durante la
     *                                 obtención de la información del Pokémon.
     */
    ReadPokemonDto getPokemonInfo(String pokemonName) throws CustomAssassinException, Exception;

}
