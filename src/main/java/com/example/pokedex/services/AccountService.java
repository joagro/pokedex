package com.example.pokedex.services;

import com.example.pokedex.dtos.AccountDTO;
import com.example.pokedex.entities.Account;
import com.example.pokedex.entities.Role;
import com.example.pokedex.repositories.AccountRepository;
import com.example.pokedex.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;


    public Account saveAccount(AccountDTO accountDTO) {

        if(StringUtils.isEmpty(accountDTO.getPassword())) { //checks if null or is an empty string
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No password entered");
        }

        if(accountDTO.getRoles().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user roles entered");
        }

        if(accountRepository.findByUsername(accountDTO.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user with that name already exists");
        }

        var validRoles = roleService.findAllRolesAsStrings();

        for(String role: accountDTO.getRoles()) {
            if(!validRoles.contains(role)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Role %s is invalid", role));

            }
        }
        Account account = new Account(accountDTO.getUsername(), passwordEncoder.encode(accountDTO.getPassword()));

        for(String rolename: accountDTO.getRoles()) {
            Optional<Role> role = roleRepository.findByName(rolename);

            if (!role.isEmpty()){
                account.addRole(role.get());
            }
        }
        var dude = accountRepository.save(account);

        return account;
    }

    public void delete(int id) {

        if(!accountRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find the account by id %s", id));
        }
        accountRepository.deleteById(id);
    }

    public List<Account> findAll(String username) {

        if(username != null) {
            var user = accountRepository.findByUsername(username).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find any accounts by username %s", username)));
            return List.of(user);
        }
        return (List<Account>) accountRepository.findAll();
    }

    public Account findbyId(int id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find the user by id %s", id)));
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find the Account by username %s", username)));
    }

    public List<Account> findAllByRole(String role) {

        if(StringUtils.isEmpty(role)) { //checks if null or is an empty string
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No role entered");
        }

        var accountRole = roleRepository.findByName(role).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No such role %s", role)));;

        if(role != null) {
            //var accounts = accountRepository.findAllByRoles(accountRole.get());
            return accountRepository.findAllByRoles(accountRole);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find any accounts by username %s", role));
    }

    public Account getActiveUser(){
        return findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public void updateAccount(int id, AccountDTO accountDTO) {

        Account activeUser = getActiveUser();
        Account account = findbyId(id);


        if (activeUser.getId() == (id) && !activeUser.getRolesAsStrings().contains("ADMIN")){
            //if the account is the active account and the user is not an Admin, set the provided password

            if(StringUtils.isEmpty(accountDTO.getPassword())) { //checks if null or is an empty string
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is invalid");
            }
            account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));

        } else if (activeUser.getRolesAsStrings().contains("ADMIN")) {

            //the current account is an admin, allows update of Password and change of userroles.
            //userroles are always overwritten

            if(accountDTO.getPassword() != null) {
                //if the password is not null, check if it's an empty string, if so encrypt and set password
                if(accountDTO.getPassword().length() > 0) {
                    account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
                }
            }

            if( accountDTO.getRoles().size() > 0) {
                //if roles is a list longer than 0, containing no invalid roles, add valid roles to a temporary list which then is set to the account
                var validRoles = roleService.findAllRolesAsStrings();
                for(String role: accountDTO.getRoles()) {
                    if(!validRoles.contains(role)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Role %s is invalid", role));
                    }
                }
                List<Role> newRoles = new ArrayList<Role>();
                for(String rolename: accountDTO.getRoles()) {
                    Optional<Role> role = roleRepository.findByName(rolename);

                    if (!role.isEmpty()){ ;
                        newRoles.add(role.get());
                    }
                }
                account.setRoles(newRoles);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        accountRepository.save(account);
    }
}
