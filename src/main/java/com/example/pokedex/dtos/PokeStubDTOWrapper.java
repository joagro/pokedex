package com.example.pokedex.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PokeStubDTOWrapper {

    @JsonProperty("results")
    private List<PokeStubDTO> pokeDTOS;

    //for some reason, this wrapper only seems to work  when fetching
    //when it has the @JsonProperty("results") or the list of PokeStubDTOs is named results
    //I have no idea why and it's never mentioned anywhere

    public PokeStubDTOWrapper() {
    }

    public PokeStubDTOWrapper(List<PokeStubDTO> pokeDTOsList) {
        this.pokeDTOS = new ArrayList<>();
    }

    public List<PokeStubDTO> getDTOs() {
        return pokeDTOS;
    }

    public void setDTOs(List<PokeStubDTO> pokeDTOsList) {
        System.out.println("using big constructor");
        this.pokeDTOS = pokeDTOsList;
    }
}
