package com.cristiand.practica.springboot.app.springboot_practica_assassins.controller;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.dto.ReadPokemonDto;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PokemonController {

    @Autowired
    PokemonService pokemonService;

    @GetMapping("/pokemon/{name}")
    public ReadPokemonDto getPokemonInfo(@PathVariable String name) {
        return pokemonService.getPokemonInfo(name);
    }
    
}
