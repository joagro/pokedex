package com.example.pokedex.dtos;

import java.util.ArrayList;
import java.util.List;

public class AccountDTO {

    private String username;
    private String password;

    private List<String> roles = new ArrayList<String>();

    public AccountDTO () {

    }

    public AccountDTO (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
