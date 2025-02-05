package org.pancakelab.model.ingredients;

public class WhippedCream extends Ingredient {
    private static final String DESCRIPTION = "Whipped cream";
    private static WhippedCream instance;

    public static WhippedCream getInstance() {
        if (instance == null) {
            synchronized (WhippedCream.class) {
                if (instance == null) {
                    instance = new WhippedCream();
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
