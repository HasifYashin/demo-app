package org.pancakelab.helpers;

import org.pancakelab.model.Order;
import org.pancakelab.model.Pancake;

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

    public static void logCancelOrder(Order order) {
        log.append("Cancelled order %s with %d pancakes ".formatted(order.getId(), order.getPancakes().size()))
                .append("for building %d, room %d.".formatted(order.getAddress().getBuilding(),
                        order.getAddress().getRoom()));
    }

    public static void logCompleteOrder(Order order) {
        log.append("Completed(Placed) order %s with %d pancakes ".formatted(order.getId(), order.getPancakes().size()))
                .append("for building %d, room %d.".formatted(order.getAddress().getBuilding(),
                        order.getAddress().getRoom()));
    }

    public static void logPrepareOrder(Order order) {
        log.append("Order %s with %d pancakes ".formatted(order.getId(), order.getPancakes().size()))
                .append("for building %d, room %d prepared.".formatted(order.getAddress().getBuilding(),
                        order.getAddress().getRoom()));
    }

    public static void logDeliverOrder(Order order) {
        log.append("Order %s with %d pancakes ".formatted(order.getId(), order.getPancakes().size()))
                .append("for building %d, room %d out for delivery.".formatted(order.getAddress().getBuilding(),
                        order.getAddress().getRoom()));
    }
}
