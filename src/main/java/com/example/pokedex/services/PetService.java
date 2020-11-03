package com.example.pokedex.services;

import com.example.pokedex.entities.Pet;
import com.example.pokedex.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public List<Pet> findAllPets(){
        return (List<Pet>) petRepository.findAll();
    }

    public Optional<Pet> findOnePet(int id) {
        return petRepository.findById(id);
    }
}
