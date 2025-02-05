package org.pancakelab.model.pancake;

import org.pancakelab.model.ingredients.DarkChocolate;
import org.pancakelab.model.ingredients.HazelNut;
import org.pancakelab.model.ingredients.MilkChocolate;
import org.pancakelab.model.ingredients.WhippedCream;

public class PancakeBuilder {
    private final Pancake pancake = new Pancake();

    public PancakeBuilder addDarkChocolate() {
        pancake.getIngredients().add(new DarkChocolate());
        return this;
    }

    public PancakeBuilder addMilkChocolate() {
        pancake.getIngredients().add(new MilkChocolate());
        return this;
    }

    public PancakeBuilder addHazelNut() {
        pancake.getIngredients().add(new HazelNut());
        return this;
    }

    public PancakeBuilder addWhippedCream() {
        pancake.getIngredients().add(new WhippedCream());
        return this;
    }

    public Pancake build() {
        return pancake;
    }

}
