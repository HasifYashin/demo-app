package org.pancakelab.model.ingredients;

public class MilkChocolate extends Chocolate {
    private static MilkChocolate instance;

    public static MilkChocolate of() {
        if (instance == null) {
            synchronized (MilkChocolate.class) {
                if (instance == null) {
                    instance = new MilkChocolate();
                }
            }
        }
        return instance;
    }
}
