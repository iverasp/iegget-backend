package no.iegget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.iegget.eat.model.Recipe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    
    @OneToMany
    private Set<Recipe> recipes = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    public String username;
    public String password;
    public String firstName;
    public String lastName;

    public Account(){} // jpa only

    public Account(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
