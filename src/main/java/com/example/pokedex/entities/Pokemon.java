package com.example.pokedex.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pokemons")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "pokemon_ability", joinColumns = @JoinColumn(name = "pokemon_id"),  inverseJoinColumns = @JoinColumn(name = "ability_id"))
    private List<Ability> abilities = new ArrayList<>();

    private String name;

    private int height;

    //Spring interpets this as the column "nationa_id"
    private int nationalId;

    private int weight;

    public Pokemon() {
    }

    public Pokemon(String name) {
        this.name = name;
    }

    public Pokemon( List<Ability> abilities, String name) {
        this.name = name;
        this.abilities = abilities;
    }

    public Pokemon(List<Ability> abilities, String name, int height, int nationalId, int weight) {
        this.abilities = abilities;
        this.name = name;
        this.height = height;
        this.nationalId = nationalId;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
