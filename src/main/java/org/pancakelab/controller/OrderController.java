package org.pancakelab.controller;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.pancakelab.exceptions.NotEnoughPancakesException;
import org.pancakelab.exceptions.OrderNotFoundException;
import org.pancakelab.model.Order;
import org.pancakelab.model.pancake.Pancake;
import org.pancakelab.model.pancake.PancakeBuilder;
import org.pancakelab.model.pancake.PancakeMap;
import org.pancakelab.service.ChefService;
import org.pancakelab.service.OrderLog;
import org.pancakelab.service.OrderService;

public class OrderController {
    private final OrderService orderService;
    private final ChefService chefService;

    private final PancakeMap pancakeMap;

    public OrderController(OrderService orderService, ChefService chefService) {
        this.orderService = orderService;
        this.chefService = chefService;
        this.pancakeMap = new PancakeMap();
    }

    public Order createOrder(int building, int room) {
        return orderService.createOrder(building, room);
    }

    public Order getOrder(UUID orderId) throws OrderNotFoundException {
        try {
            return orderService.getOrders().stream().filter(o -> o.getId().equals(orderId)).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new OrderNotFoundException();
        }

    }

    public void addPancake(String type, UUID orderId, int count) throws OrderNotFoundException {
        Order order = getOrder(orderId);
        // TODO: Invalid exception
        Pancake pancake = pancakeMap.getPancake(type);
        orderService.addPancakes(order, pancake, count);
    }

    public void removePancakes(String type, UUID orderId, int count)
            throws NotEnoughPancakesException, OrderNotFoundException {
        Order order = getOrder(orderId);
        Pancake pancake = pancakeMap.getPancake(type);
        orderService.removePancakes(pancake, order, count);
    }

    public void completeOrder(UUID orderId) throws OrderNotFoundException {
        Order order = getOrder(orderId);
        orderService.completeOrder(order);
        chefService.addOrder(order);
    }

    public Set<UUID> listCompletedOrders() {
        return chefService.getOrders().stream().map(Order::getId).collect(Collectors.toSet());
    }
}
