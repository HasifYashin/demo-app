package org.pancakelab.helpers;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.pancakelab.enums.IngredientsEnum;
import org.pancakelab.exceptions.InvalidIngredientInputException;

public class IngredientsValidator {

    public static void validateIngredients(Set<String> ingredients) throws InvalidIngredientInputException {
        if (ingredients.size() == 0)
            throw new InvalidIngredientInputException();
        Set<String> validIngredients = Arrays.stream(IngredientsEnum.values()).map(IngredientsEnum::name)
                .collect(Collectors.toSet());
        for (String ingredient : ingredients) {
            if (!validIngredients.contains(ingredient))
                throw new InvalidIngredientInputException();
        }
    }
}
