package com.example.pokedex.controllers;

import com.example.pokedex.entities.Pokemon;
import com.example.pokedex.services.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pokemon")
@Tag(description = "Pokemon controller, allows anyone to request Pokemons", name = "Pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    @Operation(summary = "Finds all the pokemon in the db matching the query parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of all Pokemon matching the search",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pokemon.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Illegal params, most likely you've used two different weight params", content = @Content)})
    public List<Pokemon> getAll(@RequestParam(required = false) String name,
                         @RequestParam(required = false) Integer exactWeight,
                         @RequestParam(required = false) Integer lessWeightThan,
                         @RequestParam(required = false) Integer greaterWeightThan){

        return pokemonService.searchByValue(name, exactWeight, lessWeightThan, greaterWeightThan);
    }

    @Operation(summary = "Finds a single pokemon by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pokemon found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pokemon.class))
                    }),
            @ApiResponse(responseCode = "404", description = "No match for this search", content = @Content)})
    @GetMapping("/{name}")
    public ResponseEntity<Pokemon> getPokemonByName(@PathVariable String name){
        return ResponseEntity.ok(pokemonService.findByName(name));
    }
}
