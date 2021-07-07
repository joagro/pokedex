package com.example.pokedex.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonController {

    @GetMapping
    public ResponseEntity<String> welcome () {
        return ResponseEntity.ok("welcome to pokemon controller");
    }
}
