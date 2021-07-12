package com.example.pokedex.dtos;

public class PokeAbilityDTO {

    private String name;
    private String URL;

    @Override
    public String toString() {
        return "PokeAbilityDTO{" +
                "name='" + name + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }

    public PokeAbilityDTO() {
    }

    public PokeAbilityDTO(String name, String URL) {
        this.name = name;
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
