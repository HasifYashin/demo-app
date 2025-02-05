package org.pancakelab.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.pancakelab.exceptions.NotEnoughPancakesException;
import org.pancakelab.exceptions.OrderNotFoundException;
import org.pancakelab.model.Order;
import org.pancakelab.model.pancake.Pancake;
import org.pancakelab.model.pancakes.PancakeRecipe;

public class OrderService {
    private List<Order> orders = new ArrayList<>();
    private Set<UUID> completedOrders = new HashSet<>();
    private Set<UUID> preparedOrders = new HashSet<>();
    private List<PancakeRecipe> pancakes = new ArrayList<>();

    public Order createOrder(int building, int room) {
        Order order = new Order(building, room);
        orders.add(order);
        return order;
    }

    public void addPancakes(Order order, Pancake pancake, int count) {
        for (int i = 0; i < count; ++i) {
            order.addPancake(pancake);
            OrderLog.logAddPancake(order, pancake);
        }
    }

    public List<String> viewOrder(UUID orderId) {
        return pancakes.stream()
                .filter(pancake -> pancake.getOrderId().equals(orderId))
                .map(PancakeRecipe::description).toList();
    }

    public void removePancakes(Pancake pancake, Order order, int count)
            throws NotEnoughPancakesException, OrderNotFoundException {
        order.removePancakes(pancake, count);
        OrderLog.logRemovePancakes(order, pancake, count);
    }

    public void cancelOrder(UUID orderId) {
        Order order = orders.stream().filter(o -> o.getId().equals(orderId)).findFirst().get();
        OrderLog.logCancelOrder(order, this.pancakes);

        pancakes.removeIf(pancake -> pancake.getOrderId().equals(orderId));
        orders.removeIf(o -> o.getId().equals(orderId));
        completedOrders.removeIf(u -> u.equals(orderId));
        preparedOrders.removeIf(u -> u.equals(orderId));

        OrderLog.logCancelOrder(order, pancakes);
    }

    public void completeOrder(Order order) {
        orders.remove(order);
    }

    public Object[] deliverOrder(UUID orderId) {
        if (!preparedOrders.contains(orderId))
            return null;

        Order order = orders.stream().filter(o -> o.getId().equals(orderId)).findFirst().get();
        List<String> pancakesToDeliver = viewOrder(orderId);
        OrderLog.logDeliverOrder(order, this.pancakes);

        pancakes.removeIf(pancake -> pancake.getOrderId().equals(orderId));
        orders.removeIf(o -> o.getId().equals(orderId));
        preparedOrders.removeIf(u -> u.equals(orderId));

        return new Object[] { order, pancakesToDeliver };
    }

    public List<Order> getOrders() {
        return orders;
    }
}
