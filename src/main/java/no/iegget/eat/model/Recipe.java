package no.iegget.eat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.iegget.model.Account;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
public class Recipe {

    @JsonIgnore
    @ManyToOne
    private Account account;

    @Id
    @GeneratedValue
    private Long id;

    private Date timeCreated;

    private Date timeEdited;

    private String name;

    private String ingredients;

    private String instructions;

    @ElementCollection
    private Set<String> sources;

    @ManyToOne
    private Recipe parent;

    @OneToMany(mappedBy = "parent")
    private Collection<Recipe> children;

    public Recipe(){} // jpa only

    public Recipe(Account account, String name) {
        this.account = account;
        this.name = name;
    }

    public Recipe(Account account, String name, String ingredients, String instructions, Set<String> sources) {
        this.account = account;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.sources = sources;
    }

    @PrePersist
    protected void onCreate() {
        timeCreated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        timeEdited = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public Date getTimeEdited() {
        return timeEdited;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public Set<String> getSources() {
        return sources;
    }
}
