package no.iegget.eat.controller;

import no.iegget.eat.model.Recipe;
import no.iegget.exception.UserNotFoundException;
import no.iegget.repository.AccountRepository;
import no.iegget.eat.repository.RecipeRepository;
import no.iegget.eat.resource.RecipeResource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eat")
public class RecipeRestController {

    private final AccountRepository accountRepository;
    private final RecipeRepository recipeRepository;

    RecipeRestController(AccountRepository accountRepository, RecipeRepository recipeRepository) {
        this.accountRepository = accountRepository;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Recipe> readRecipes() {
        return recipeRepository.findAll();
    }

    /*
    @RequestMapping(method = RequestMethod.GET)
    Resources<RecipeResource> readRecipes() {

        List<RecipeResource> recipeResourceList = recipeRepository
                .findAll()
                .stream()
                .map(RecipeResource::new)
                .collect(Collectors.toList());

        return new Resources<>(recipeResourceList);
    }
    */

    @RequestMapping(method = RequestMethod.GET, value = "/{recipeId}")
    public Recipe readRecipe(@PathVariable Long recipeId) {
        return this.recipeRepository.findOne(recipeId);
    }

    private void validateUser(String userId) {
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
