package com.example.pokedex.controllers;

import com.example.pokedex.entities.Pokemon;
import com.example.pokedex.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    public List<Pokemon> getAll(@RequestParam(required = false) String name,
                         @RequestParam(required = false) Integer exactWeight,
                         @RequestParam(required = false) Integer lessWeightThan,
                         @RequestParam(required = false) Integer greaterWeightThan){

        return pokemonService.searchByValue(name, exactWeight, lessWeightThan, greaterWeightThan);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Pokemon> getPokemonByName(@PathVariable String name){
        return ResponseEntity.ok(pokemonService.findByName(name));
    }
}
