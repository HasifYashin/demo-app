package org.pancakelab.model.ingredients;

public class DarkChocolate extends Chocolate {
    private static DarkChocolate instance;

    public static DarkChocolate of() {
        if (instance == null) {
            synchronized (DarkChocolate.class) {
                if (instance == null) {
                    instance = new DarkChocolate();
                }
            }
        }
        return instance;
    }
}
