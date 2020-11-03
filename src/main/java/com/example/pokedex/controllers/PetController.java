package com.example.pokedex.controllers;

import com.example.pokedex.entities.Pet;
import com.example.pokedex.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController {

    @Autowired
    PetService petService;

    @GetMapping("/rest/pets")
    public List<Pet> getAllPets() {
        System.out.println("dude");
        return petService.findAllPets();
    }
}
