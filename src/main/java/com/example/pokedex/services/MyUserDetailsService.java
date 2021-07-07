package com.example.pokedex.services;

import com.example.pokedex.entities.Account;
import com.example.pokedex.entities.Role;
//import com.example.pokedex.entities.User;
import com.example.pokedex.repositories.AccountRepository;
import com.example.pokedex.repositories.RoleRepository;
//mport com.example.pokedex.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByUsername(username);
        if(account == null) {
            throw new UsernameNotFoundException("User name " + username + " not found");
        }
        return new org.springframework.security.core.userdetails.User(account.getUsername(),
                account.getPassword(), getGrantedAuthorities(account));
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(Account account) {
        //return user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return account.getRolesAsStrings().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // ROLE_ADMIN -> ADMIN
                .collect(Collectors.toList());
    }

    @PostConstruct
    private void createAdminAccount() {

        if (accountRepository.findByUsername("admin").isEmpty()) {
            System.out.println("No boss found, creating");

            List<Role> roles = new ArrayList<Role>();
            roles.add(roleRepository.findByName("USER").get());
            roles.add(roleRepository.findByName("ADMIN").get());

            Account admin = new Account("admin", passwordEncoder.encode("admin"), roles);

            accountRepository.save(admin);
        }
        System.out.println("The boss is present");
    }
}
