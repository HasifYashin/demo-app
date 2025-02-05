package org.pancakelab.service;

import java.util.ArrayList;
import java.util.List;

import org.pancakelab.model.Order;

/**
 * Class handling delivery of orders.
 * It receives an order once the chef has prepared it.
 */

public class DeliveryService {
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void deliverOrder(Order order) {
        OrderLog.logDeliverOrder(order);
        orders.remove(order);
    }

}
