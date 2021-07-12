package com.example.pokedex.repositories;

import com.example.pokedex.entities.Pokemon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends CrudRepository<Pokemon, Integer> {
    Optional<Pokemon> findByName(String name);
    boolean existsByName(String name);

    long deleteByName(String name);

    Optional<Pokemon> findByNameOrNationalId(String name, String nationalId);

    @Override
    List<Pokemon> findAll();
    List<Pokemon> findAllByName(String name);
    List<Pokemon> findAllByWeight(Integer weight);
    List<Pokemon> findAllByNameIsContaining(String name);


    List<Pokemon> findAllByNameIsContainingAndWeight(String name, Integer weight);

    List<Pokemon> findAllByNameIsContainingAndWeightIsGreaterThanEqual(String name, Integer weight);
    List<Pokemon> findAllByNameIsContainingAndWeightIsGreaterThanEqualOrderByWeightAsc(String name, Integer weight);

    List<Pokemon> findAllByNameIsContainingAndWeightIsLessThanEqualOrderByWeightAsc(String name, Integer weight);
    List<Pokemon> findAllByWeightIsGreaterThanEqual(Integer weight);
    List<Pokemon> findAllByWeightIsGreaterThanEqualOrderByWeightAsc(Integer weight);
    List<Pokemon> findAllByWeightIsLessThanEqualOrderByWeightDesc(Integer weight);

    List<Pokemon> findAllByNameIsContainingAndWeightIsLessThanEqualOrderByWeightDesc(String name, Integer lessWeightThan);
}
