package org.pancakelab.model.pancake;

import java.util.HashSet;
import java.util.Set;

import org.pancakelab.model.ingredients.Ingredient;

public class Pancake {
    private final Set<Ingredient> ingredients = new HashSet<>();

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

}
