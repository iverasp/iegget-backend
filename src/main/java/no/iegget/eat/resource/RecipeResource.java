package no.iegget.eat.resource;

import no.iegget.eat.controller.RecipeRestController;
import no.iegget.eat.model.Recipe;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class RecipeResource extends ResourceSupport {

    private final Recipe recipe;

    public RecipeResource(Recipe recipe) {
        String username = recipe.getAccount().getUsername();
        this.recipe = recipe;
        this.add(linkTo(methodOn(RecipeRestController.class)
                .readRecipe(recipe.getId())).withSelfRel());
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
