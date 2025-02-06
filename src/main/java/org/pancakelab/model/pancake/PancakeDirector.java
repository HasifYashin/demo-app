package org.pancakelab.model.pancake;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.pancakelab.exceptions.InvalidIngredientInputException;
import org.pancakelab.model.ingredients.DarkChocolate;
import org.pancakelab.model.ingredients.HazelNut;
import org.pancakelab.model.ingredients.IngredientsEnum;
import org.pancakelab.model.ingredients.MilkChocolate;
import org.pancakelab.model.ingredients.WhippedCream;

public class PancakeDirector {
    private PancakeBuilder builder;

    public PancakeDirector() {
        builder = new PancakeBuilder();
    }

    private void validateIngredients(Set<String> ingredients) throws InvalidIngredientInputException {
        if (ingredients.size() == 0)
            throw new InvalidIngredientInputException();
        Set<String> validIngredients = Arrays.stream(IngredientsEnum.values()).map(IngredientsEnum::name)
                .collect(Collectors.toSet());
        for (String ingredient : ingredients) {
            if (!validIngredients.contains(ingredient))
                throw new InvalidIngredientInputException();
        }
    }

    public Pancake makePancake(Set<String> ingredients) throws InvalidIngredientInputException {
        validateIngredients(ingredients);
        builder.reset();
        for (String ingredient : ingredients) {
            if (ingredient.equals(IngredientsEnum.DARKCHOCOLATE.name()))
                builder.addIngredient(DarkChocolate.getInstance());
            if (ingredient.equals(IngredientsEnum.MILKCHOCOLATE.name()))
                builder.addIngredient(MilkChocolate.getInstance());
            if (ingredient.equals(IngredientsEnum.HAZELNUT.name()))
                builder.addIngredient(HazelNut.getInstance());
            if (ingredient.equals(IngredientsEnum.WHIPPEDCREAM.name()))
                builder.addIngredient(WhippedCream.getInstance());
        }
        return builder.build();
    }
}
