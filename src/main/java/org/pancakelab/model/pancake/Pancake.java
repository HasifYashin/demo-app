package org.pancakelab.model.pancake;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.pancakelab.model.ingredients.Ingredient;

public class Pancake {
    private final Set<Ingredient> ingredients = new HashSet<>();

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        return ingredients.stream().map(Ingredient::getDescription).collect(Collectors.joining(" ")) + " Pancake";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pancake other = (Pancake) obj;
        if (ingredients == null) {
            if (other.ingredients != null)
                return false;
        } else if (!ingredients.equals(other.ingredients))
            return false;
        return true;
    }

}
