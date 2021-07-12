package com.example.pokedex.services;

import com.example.pokedex.dtos.*;
import com.example.pokedex.entities.Ability;
import com.example.pokedex.entities.Pokemon;
import com.example.pokedex.repositories.AbilityRepository;
import com.example.pokedex.repositories.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonConsumerService {

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    private final RestTemplate restTemplate;

    private String pokeUrl = "https://pokeapi.co/api/v2/pokemon/";

    public PokemonConsumerService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void getAll() {

        //note, only the first 151 pokemon are the real ones, the other 967 are fake

        PokeStubDTOWrapper wrappedPokemonStubs = restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon?limit=1118", PokeStubDTOWrapper.class);
        List<PokeStubDTO> pokemonStubList = wrappedPokemonStubs.getDTOs();

        for (PokeStubDTO pokeStub: pokemonStubList) {
            PokeDTO pokeDTO = restTemplate.getForObject(pokeStub.getUrl(), PokeDTO.class);

            Optional<Pokemon> pokemonInDB = pokemonRepository.findByName(pokeDTO.getName());

            if(pokemonInDB.isEmpty()){
                savePokemon(pokeDTO);
                System.out.println("saved: " + pokeDTO.getName());

            } else {
                System.out.println(pokeDTO.getName()+ " already exists in DB");
            }
        }
    }

    public Pokemon savePokemon (PokeDTO pokeDTO) {

        List<Ability> pokemonAbilities = new ArrayList<Ability>();

        for (PokeAbilityDTOwrapper pokeAbilityDTOwrapper : pokeDTO.getAbilities()) {

            var abilityExists = abilityRepository.findByName(pokeAbilityDTOwrapper.getAbility().getName());
            if (abilityExists.isEmpty()) {
                Ability newAbility = abilityRepository.save(new Ability(pokeAbilityDTOwrapper.getAbility().getName(), pokeAbilityDTOwrapper.getAbility().getURL()));
                pokemonAbilities.add(newAbility);

            } else {
                pokemonAbilities.add(abilityExists.get());
            }
        }
        return pokemonRepository.save(new Pokemon(pokemonAbilities, pokeDTO.getName(), pokeDTO.getHeight(), pokeDTO.getPokemonNumber(), pokeDTO.getWeight()));
    }

    public Pokemon getPokemon(String pokemonName) {
        String fullURL = pokeUrl + pokemonName;

        var pokemonDTO = restTemplate.getForObject(fullURL, PokeDTO.class);

        if(pokemonDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found.");
        }
        return savePokemon(pokemonDTO);
    }
}
