package com.example.pokedex.dtos;

public class PokeStubDTO {

    private String name;

    private String url;

    public PokeStubDTO(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public PokeStubDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
