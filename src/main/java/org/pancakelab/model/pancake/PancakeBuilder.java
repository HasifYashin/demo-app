package org.pancakelab.model.pancake;

import org.pancakelab.model.ingredients.DarkChocolate;
import org.pancakelab.model.ingredients.HazelNut;
import org.pancakelab.model.ingredients.MilkChocolate;
import org.pancakelab.model.ingredients.WhippedCream;

public class PancakeBuilder {
    private Pancake pancake = new Pancake();

    public void reset() {
        pancake = new Pancake();
    }

    public PancakeBuilder addDarkChocolate() {
        pancake.getIngredients().add(DarkChocolate.of());
        return this;
    }

    public PancakeBuilder addMilkChocolate() {
        pancake.getIngredients().add(MilkChocolate.of());
        return this;
    }

    public PancakeBuilder addHazelNut() {
        pancake.getIngredients().add(HazelNut.of());
        return this;
    }

    public PancakeBuilder addWhippedCream() {
        pancake.getIngredients().add(WhippedCream.of());
        return this;
    }

    public Pancake build() {
        return pancake;
    }

}
