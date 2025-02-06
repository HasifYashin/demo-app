package org.pancakelab.service;

import java.util.ArrayList;
import java.util.List;

import org.pancakelab.exceptions.NotEnoughPancakesException;
import org.pancakelab.exceptions.OrderNotFoundException;
import org.pancakelab.model.Order;
import org.pancakelab.model.pancake.Pancake;

public class OrderService {
    private List<Order> orders = new ArrayList<>();

    public synchronized Order createOrder(int building, int room) {
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

    public void removePancakes(Pancake pancake, Order order, int count)
            throws NotEnoughPancakesException, OrderNotFoundException {
        order.removePancakes(pancake, count);
        OrderLog.logRemovePancakes(order, pancake, count);
    }

    /*
     * Cancels the order based on user input. Order will not proceed further.
     */
    public synchronized void cancelOrder(Order order) {
        OrderLog.logCancelOrder(order);
        orders.remove(order);
    }

    /*
     * Specifies that user configuration of the order is complete
     */
    public synchronized void completeOrder(Order order) {
        OrderLog.logCompleteOrder(order);
        orders.remove(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
