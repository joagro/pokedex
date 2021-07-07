package com.example.pokedex.entities;

import com.example.pokedex.repositories.RoleRepository;
import com.example.pokedex.services.RoleService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    private String username;

    @JsonIgnore
    private String password;

    //private RoleService roleService;

    //@Autowired
    //private RoleRepository roleRepository;

    /*

    @ManyToMany
    @JoinTable(
        name="EMP_PROJ",
        joinColumns=@JoinColumn(name="EMP_ID", referencedColumnName="ID"),
        inverseJoinColumns=@JoinColumn(name="PROJ_ID", referencedColumnName="ID"))


    @ManyToMany
    @JoinTable(
            name="account_role",
            joinColumns=@JoinColumn(name="account_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id"))
     */

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "account_role", joinColumns = @JoinColumn(name = "account_id"),  inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;

    }

    //JsonProperty
    @JsonIgnore
    public List<Role> getRoles() {
        return this.roles;

        /*
        List<String> roleStrings = new ArrayList<>();

        for(Role role: roles) {
            roleStrings.add(role.getName());
        }

        System.out.println(this.roles);

        return roleStrings;

         */
    }

    @JsonProperty(value="roles")
    public List<String> getRolesAsStrings() {

        List<String> roleStrings = new ArrayList<>();

        for(Role role: roles) {
            roleStrings.add(role.getName());
        }

        return roleStrings;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;

    }

    public void addRole(Role role) {
        this.roles.add(role);
        //role.getAccounts.add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        //role.getAccounts().remove(this);
    }
    /*
    public void remove() {
        for(Role role : new ArrayList<>(roles)) {
            removeRole(role);
        }
    }
     */

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
