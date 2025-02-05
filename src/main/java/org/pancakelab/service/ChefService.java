package org.pancakelab.service;

import java.util.ArrayList;
import java.util.List;

import org.pancakelab.model.Order;

/**
 * Receives orders once user has completed customization and order is now sent
 * for preparation
 */
public class ChefService {
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
