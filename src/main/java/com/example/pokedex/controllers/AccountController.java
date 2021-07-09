package com.example.pokedex.controllers;

import com.example.pokedex.dtos.AccountDTO;
import com.example.pokedex.entities.Account;
import com.example.pokedex.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Operation(summary = "Creates a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class), examples = {})
                    }),
            @ApiResponse(responseCode = "400", description = "Failed to create user account, because of faulty request body data or because the username is taken", content = @Content),
            @ApiResponse(responseCode = "401", description = "The user attempting this operation is not authenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "The user attempting this operation is not authorized, need Admin status to take this action", content = @Content)
    })
    @PostMapping()
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Account> addUser(@RequestBody AccountDTO accountDTO) {

        return ResponseEntity.ok(accountService.saveAccount(accountDTO));
    }

    @Operation(summary = "gets either a list of all accounts or a list of a single one with the specified username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account/accounts were successfully fetched", content = @Content),
            @ApiResponse(responseCode = "401", description = "The user attempting this operation is not authenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "The user attempting this operation is not authorized, need Admin status to take this action", content = @Content),
            @ApiResponse(responseCode = "404", description = "No user with such username", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Account>> findAllAccounts(@RequestParam(required = false) String username) {

        return ResponseEntity.ok(accountService.findAll(username));
    }

    @Operation(summary = "gets either a list of all accounts or a list of a single one with the specified username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account successfully created", content = @Content),
            @ApiResponse(responseCode = "401", description = "The user attempting this operation is not authenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "The user attempting this operation is not authorized, need Admin status to take this action", content = @Content),
            @ApiResponse(responseCode = "404", description = "No user with such username", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@Parameter(name = "id",description = "account ID", example = "id=45") @PathVariable int id){
        return ResponseEntity.ok(accountService.findbyId(id));
    }

    @Operation(summary = "Updates an existing user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class))
                    }),
            @ApiResponse(responseCode = "401", description = "The user is not authenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "The user is not authorized, needs Admin status to change the password of another user acount or change the account's authorities", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to update user account, because of faulty request body data", content = @Content)
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@Parameter(name = "id",description = "account ID", example = "id=45") @PathVariable int id, @RequestBody AccountDTO accountDTO) {
        accountService.updateAccount(id, accountDTO);

    }

    @Operation(summary = "deletes an existing user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User account successfully deleted"),
            @ApiResponse(responseCode = "401", description = "The user is not authenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "The user is not authorized, needs Admin status to remove an account", content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to update user account, because of faulty request body data", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@Parameter(name = "id",description = "account ID", example = "id=45") @PathVariable int id){
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
