package org.pancakelab.service;

import java.util.ArrayList;
import java.util.List;

import org.pancakelab.helpers.OrderLog;
import org.pancakelab.model.Order;

/**
 * Class handling preperation of orders.
 * It receives an order once a user has completed configuring it.
 */
public class ChefService {
    private final List<Order> orders = new ArrayList<>();

    public synchronized void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    /*
     * Completes the preparation of the order.
     */
    public synchronized void prepareOrder(Order order) {
        OrderLog.logPrepareOrder(order);
        orders.remove(order);
    }
}
