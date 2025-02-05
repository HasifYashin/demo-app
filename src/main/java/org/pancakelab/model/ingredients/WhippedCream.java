package org.pancakelab.model.ingredients;

public class WhippedCream extends Ingredient {
    private static WhippedCream instance;

    public static WhippedCream of() {
        if (instance == null) {
            synchronized (WhippedCream.class) {
                if (instance == null) {
                    instance = new WhippedCream();
                }
            }
        }
        return instance;
    }
}
