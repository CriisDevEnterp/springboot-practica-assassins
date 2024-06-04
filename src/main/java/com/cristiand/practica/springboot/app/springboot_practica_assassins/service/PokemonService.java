package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.ReadPokemonDto;

public interface PokemonService {
    
    ReadPokemonDto getPokemonInfo(String pokemonName);
    
}
