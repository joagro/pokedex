package com.example.pokedex.services;

import com.example.pokedex.entities.Pokemon;
import com.example.pokedex.repositories.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PokemonService {

    @Autowired
    private PokemonConsumerService pokemonConsumerService;

    @Autowired
    private PokemonRepository pokemonRepository;


    public Pokemon findByName(String pokemonName) {

        var dbPokemon = pokemonRepository.findByName(pokemonName);

        if (dbPokemon.isEmpty()) {
            System.out.println("no such pokemon");
            return pokemonConsumerService.getPokemon(pokemonName);

        } else {
            return dbPokemon.get();
        }
    }

    public void deletePokemon(String pokemonName) {
        if(!pokemonRepository.existsByName(pokemonName)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find the pokemon by name %s", pokemonName));
        }
        pokemonRepository.deleteById(pokemonRepository.findByName(pokemonName).get().getId());
    }

    public List<Pokemon> searchByValue(String name, Integer exactWeight, Integer lessWeightThan, Integer greaterWeightThan) {

        System.out.println(pokemonRepository.count());

        if(pokemonRepository.count() < 1118){
            pokemonConsumerService.getAll();
        }

        if(exactWeight != null && lessWeightThan == null && greaterWeightThan == null) {
            //find by exact weight, with or without name

            if (name != null) {
                return pokemonRepository.findAllByNameIsContainingAndWeight(name, exactWeight);
            }
            return pokemonRepository.findAllByWeight(exactWeight);
        }

        if(lessWeightThan != null && exactWeight == null && greaterWeightThan == null) {
            //find by less weight than, with or without name

            if (name != null) {
                return pokemonRepository.findAllByNameIsContainingAndWeightIsLessThanEqualOrderByWeightDesc(name, lessWeightThan);
            }
            return pokemonRepository.findAllByWeightIsLessThanEqualOrderByWeightDesc(lessWeightThan);
        }

        if(greaterWeightThan != null && exactWeight == null && lessWeightThan == null) {

            if (name != null) {
                return pokemonRepository.findAllByNameIsContainingAndWeightIsGreaterThanEqualOrderByWeightAsc(name, greaterWeightThan);
            }
            return pokemonRepository.findAllByWeightIsGreaterThanEqualOrderByWeightAsc(greaterWeightThan);
        }

        if (exactWeight == null && lessWeightThan == null && greaterWeightThan == null ){
            //find by name only, or if no name is provided, get all pokemons

            if(name != null){
                return pokemonRepository.findAllByNameIsContaining(name);
            }
            return pokemonRepository.findAll();
        }
        //if nothing else, it's because two or more weight variables were selected, which doesn't make sense
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "two or more weight variables are defined");
    }
}
