package org.pancakelab.service;

import java.util.ArrayList;
import java.util.List;

import org.pancakelab.model.Order;

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
