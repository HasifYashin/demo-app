package org.pancakelab.model.ingredients;

public class HazelNut extends Ingredient {
    private static HazelNut instance;

    public static HazelNut of() {
        if (instance == null) {
            synchronized (DarkChocolate.class) {
                if (instance == null) {
                    instance = new HazelNut();
                }
            }
        }
        return instance;
    }

}
