package com.example.pokedex.repositories;

import com.example.pokedex.entities.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends CrudRepository<Pet, Integer> {
}
