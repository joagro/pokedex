package com.example.pokedex.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokeDTO {

    @JsonProperty("abilities")
    private List<PokeAbilityDTOwrapper> abilities;

    private int height;

    @JsonProperty("id")
    private int pokemonNumber; //keep this pokedex id?

    private String name;

    private int order;

    private int weight;

    public PokeDTO() {
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "PokeDTO{" +
                "abilities=" + abilities +
                ", height=" + height +
                ", pokemonNumber="  + pokemonNumber +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", weight=" + weight +
                '}';
    }

    public List<PokeAbilityDTOwrapper> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<PokeAbilityDTOwrapper> abilities) {
        this.abilities = abilities;
    }

    public int getPokemonNumber() {
        return pokemonNumber;
    }

    public void setPokemonNumber(int pokemonNumber) {
        this.pokemonNumber = pokemonNumber;
    }

}
