package com.example.pokedex.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokeAbilityDTOwrapper {

    @JsonProperty("ability")
    private PokeAbilityDTO ability;

    @JsonProperty("is_hidden")
    private boolean isHidden;

    private int slot;


    public PokeAbilityDTOwrapper() {
    }

    public PokeAbilityDTOwrapper(PokeAbilityDTO ability, boolean isHidden, int slot) {
        this.ability = ability;
        this.isHidden = isHidden;
        this.slot = slot;
    }

    public PokeAbilityDTO getAbility() {
        return ability;
    }

    public void setAbility(PokeAbilityDTO ability) {
        this.ability = ability;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "PokeAbilityDTOwrapper{" +
                "ability=" + ability +
                ", isHidden=" + isHidden +
                ", slot=" + slot +
                '}';
    }
}
