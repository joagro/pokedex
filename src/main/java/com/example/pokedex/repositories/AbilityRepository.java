package com.example.pokedex.repositories;

import com.example.pokedex.entities.Ability;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbilityRepository extends CrudRepository<Ability, Integer> {
    Optional<Ability> findByName(String name);
}
