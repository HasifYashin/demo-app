package org.pancakelab.service;

import org.pancakelab.model.Order;
import org.pancakelab.model.pancake.Pancake;
import org.pancakelab.model.pancakes.PancakeRecipe;

import java.util.List;

public class OrderLog {
    private static final StringBuilder log = new StringBuilder();

    public static void logAddPancake(Order order, Pancake pancake) {
        log.append("Added pancake with description '%s' ".formatted(pancake.getDescription()))
                .append("to order %s containing %d pancakes, ".formatted(order.getId(), order.getPancakes().size()))
                .append("for building %d, room %d.".formatted(order.getAddress().getBuilding(),
                        order.getAddress().getRoom()));
    }

    public static void logRemovePancakes(Order order, Pancake pancake, int count) {
        log.append("Removed %d pancake(s) with description '%s' ".formatted(count, pancake.getDescription()))
                .append("from order %s now containing %d pancakes, ".formatted(order.getId(),
                        order.getPancakes().size()))
                .append("for building %d, room %d.".formatted(order.getAddress().getBuilding(),
                        order.getAddress().getRoom()));
    }

    public static void logCancelOrder(Order order, List<PancakeRecipe> pancakes) {
        long pancakesInOrder = pancakes.stream().filter(p -> p.getOrderId().equals(order.getId())).count();
        log.append("Cancelled order %s with %d pancakes ".formatted(order.getId(), pancakesInOrder))
                .append("for building %d, room %d.".formatted(order.getAddress().getBuilding(),
                        order.getAddress().getRoom()));
    }

    public static void logDeliverOrder(Order order, List<PancakeRecipe> pancakes) {
        long pancakesInOrder = pancakes.stream().filter(p -> p.getOrderId().equals(order.getId())).count();
        log.append("Order %s with %d pancakes ".formatted(order.getId(), pancakesInOrder))
                .append("for building %d, room %d out for delivery.".formatted(order.getAddress().getBuilding(),
                        order.getAddress().getRoom()));
    }
}
