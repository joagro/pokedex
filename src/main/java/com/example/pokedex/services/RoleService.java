package com.example.pokedex.services;

import com.example.pokedex.entities.Role;
//import com.example.pokedex.entities.User;
import com.example.pokedex.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);

    }

    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    public List<String> findAllRolesAsStrings() {

        List<Role> roles = findAll();

        List<String> strings  = roles.stream().map(x -> x.getName()).collect(Collectors.toList());

        return strings ;
    }
}
