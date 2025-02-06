package org.pancakelab.model.pancake;

import org.pancakelab.model.ingredients.Ingredient;

public class PancakeBuilder {
    private Pancake pancake = new Pancake();

    public void reset() {
        pancake = new Pancake();
    }

    public PancakeBuilder addIngredient(Ingredient ingredient) {
        pancake.getIngredients().add(ingredient);
        return this;
    }

    public Pancake build() {
        return pancake;
    }

}
