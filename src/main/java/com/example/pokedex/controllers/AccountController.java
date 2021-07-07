package com.example.pokedex.controllers;

import com.example.pokedex.dtos.AccountDTO;
import com.example.pokedex.entities.Account;
import com.example.pokedex.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> addUser(@RequestBody AccountDTO accountDTO) {

        return ResponseEntity.ok(accountService.saveAccount(accountDTO));
    }

    @GetMapping
    public ResponseEntity<List<Account>> findAllAccounts(@RequestParam(required = false) String username) {

        return ResponseEntity.ok(accountService.findAll(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable int id){
        return ResponseEntity.ok(accountService.findbyId(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)    public void updateAccount(@PathVariable int id, @RequestBody AccountDTO accountDTO) {
        accountService.updateAccount(id, accountDTO);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable int id){
        accountService.delete(id);
    }
    /*
    @GetMapping("/roles/{role}")
    public ResponseEntity<List<Account>> getAccountsByRole(@PathVariable String role) {

        var accounts = accountService.findAllByRole(role);

        return ResponseEntity.ok(accounts);
    }

    */
}
