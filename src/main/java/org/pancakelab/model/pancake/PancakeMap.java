package org.pancakelab.model.pancake;

import java.util.HashMap;
import java.util.Map;

import org.pancakelab.exceptions.NoSuchPancakeTypeException;

public class PancakeMap {
    private final static Map<String, Pancake> pancakes = new HashMap<>();
    static {
        PancakeDirector pancakeDirector = new PancakeDirector();
        pancakes.put("DARK_CHOCOLATE_PANCAKE", pancakeDirector.makeDarkChocolatePancake());
        pancakes.put("MILK_CHOCOLATE_PANCAKE", pancakeDirector.makeMilkChocolatePancake());
        pancakes.put("MILK_CHOCOLATE_HAZELNUTS_PANCAKE", pancakeDirector.makeMilkChocolateHazelnutPancake());
    }

    public static Pancake getPancake(String type) throws NoSuchPancakeTypeException {
        if (!pancakes.containsKey(type))
            throw new NoSuchPancakeTypeException();
        return pancakes.get(type);
    }
}
