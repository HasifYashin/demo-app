package org.pancakelab.model.pancake;

public class PancakeDirector {
    private PancakeBuilder builder;

    public PancakeDirector() {
        builder = new PancakeBuilder();
    }

    public Pancake makeDarkChocolatePancake() {
        builder.reset();
        return builder.addDarkChocolate().build();
    }

    public Pancake makeDarkChocolateWhippedCreamPancake() {
        builder.reset();
        return builder.addDarkChocolate().addWhippedCream().build();
    }

    public Pancake makeDarkChocolateWhippedCreamHazelnutsPancake() {
        builder.reset();
        return builder.addDarkChocolate().addWhippedCream().addHazelNut().build();
    }

    public Pancake makeMilkChocolatePancake() {
        builder.reset();
        return builder.addMilkChocolate().build();
    }

    public Pancake makeMilkChocolateHazelnutPancake() {
        builder.reset();
        return builder.addMilkChocolate().addHazelNut().build();
    }

}
