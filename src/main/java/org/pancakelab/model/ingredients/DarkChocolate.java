package org.pancakelab.model.ingredients;

public class DarkChocolate extends Chocolate {
    private static final String DESCRIPTION = "Dark Chocolate";
    private static DarkChocolate instance;

    public static DarkChocolate getInstance() {
        if (instance == null) {
            synchronized (DarkChocolate.class) {
                if (instance == null) {
                    instance = new DarkChocolate();
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
