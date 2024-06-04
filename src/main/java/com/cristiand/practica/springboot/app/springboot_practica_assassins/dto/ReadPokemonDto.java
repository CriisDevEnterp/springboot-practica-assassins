package com.cristiand.practica.springboot.app.springboot_practica_assassins.dto;

import java.util.List;

public record ReadPokemonDto(
    int id, 
    Boolean is_default, 
    String location_area_encounters,
    List<Object> moves,
    String name, 
    int order,
    List<Object> past_abilities,
    List<Object> past_types,
    SpeciesDto species,
    Object sprites,
    List<Object> stats,
    List<Object> types,
    int weight
) { }
