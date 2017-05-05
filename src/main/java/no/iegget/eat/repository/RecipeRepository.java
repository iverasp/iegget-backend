package no.iegget.eat.repository;

import no.iegget.eat.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Collection<Recipe> findByAccountUsername(String username);

}
