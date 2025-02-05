package org.pancakelab.model.ingredients;

public class MilkChocolate extends Chocolate {
    private static final String DESCRIPTION = "Milk Chocolate";
    private static MilkChocolate instance;

    public static MilkChocolate getInstance() {
        if (instance == null) {
            synchronized (MilkChocolate.class) {
                if (instance == null) {
                    instance = new MilkChocolate();
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
