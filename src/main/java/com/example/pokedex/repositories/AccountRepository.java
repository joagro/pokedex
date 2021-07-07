package com.example.pokedex.repositories;

import com.example.pokedex.entities.Account;
import com.example.pokedex.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
    List<Account> findAllByRoles(Role role);
}