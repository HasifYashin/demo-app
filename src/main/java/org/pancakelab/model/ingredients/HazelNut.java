package org.pancakelab.model.ingredients;

public class HazelNut extends Ingredient {
    private static final String DESCRIPTION = "Hazelnut";
    private static HazelNut instance;

    public static HazelNut getInstance() {
        if (instance == null) {
            synchronized (DarkChocolate.class) {
                if (instance == null) {
                    instance = new HazelNut();
                }
            }
        }
        return instance;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
