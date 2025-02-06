package org.pancakelab.helpers;

import java.util.Set;

import org.pancakelab.enums.IngredientsEnum;
import org.pancakelab.exceptions.InvalidIngredientInputException;
import org.pancakelab.model.Pancake;
import org.pancakelab.model.ingredients.DarkChocolate;
import org.pancakelab.model.ingredients.HazelNut;
import org.pancakelab.model.ingredients.Ingredient;
import org.pancakelab.model.ingredients.MilkChocolate;
import org.pancakelab.model.ingredients.WhippedCream;

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

    public Pancake makePancake(Set<String> ingredients) throws InvalidIngredientInputException {
        reset();
        for (String ingredient : ingredients) {
            if (ingredient.equals(IngredientsEnum.DARKCHOCOLATE.name()))
                addIngredient(DarkChocolate.getInstance());
            if (ingredient.equals(IngredientsEnum.MILKCHOCOLATE.name()))
                addIngredient(MilkChocolate.getInstance());
            if (ingredient.equals(IngredientsEnum.HAZELNUT.name()))
                addIngredient(HazelNut.getInstance());
            if (ingredient.equals(IngredientsEnum.WHIPPEDCREAM.name()))
                addIngredient(WhippedCream.getInstance());
        }
        return build();
    }

}
