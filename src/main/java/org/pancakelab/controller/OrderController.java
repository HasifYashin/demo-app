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
import org.pancakelab.service.DeliveryService;
import org.pancakelab.service.OrderLog;
import org.pancakelab.service.OrderService;

public class OrderController {
    private final OrderService orderService;
    private final ChefService chefService;
    private final DeliveryService deliveryService;

    public OrderController(OrderService orderService, ChefService chefService, DeliveryService deliveryService) {
        this.orderService = orderService;
        this.chefService = chefService;
        this.deliveryService = deliveryService;
    }

    public Order createOrder(int building, int room) {
        return orderService.createOrder(building, room);
    }

    public Order getNotCompletedOrder(UUID orderId) throws OrderNotFoundException {
        try {
            return orderService.getOrders().stream().filter(o -> o.getId().equals(orderId)).findFirst().get();
        } catch (NoSuchElementException e3) {
            throw new OrderNotFoundException();
        }
    }

    public Order getNotPreparedOrder(UUID orderId) throws OrderNotFoundException {
        try {
            return chefService.getOrders().stream().filter(o -> o.getId().equals(orderId)).findFirst().get();
        } catch (NoSuchElementException e1) {
            throw new OrderNotFoundException();
        }
    }

    public Order getNotDeliveredOrder(UUID orderId) throws OrderNotFoundException {
        try {
            return deliveryService.getOrders().stream().filter(o -> o.getId().equals(orderId)).findFirst().get();
        } catch (NoSuchElementException e1) {
            throw new OrderNotFoundException();
        }
    }

    public void addPancake(String type, UUID orderId, int count) throws OrderNotFoundException {
        Order order = getNotCompletedOrder(orderId);
        // TODO: Invalid exception
        Pancake pancake = PancakeMap.getPancake(type);
        orderService.addPancakes(order, pancake, count);
    }

    public void removePancakes(String type, UUID orderId, int count)
            throws NotEnoughPancakesException, OrderNotFoundException {
        Order order = getNotCompletedOrder(orderId);
        Pancake pancake = PancakeMap.getPancake(type);
        orderService.removePancakes(pancake, order, count);
    }

    public void completeOrder(UUID orderId) throws OrderNotFoundException {
        Order order = getNotCompletedOrder(orderId);
        orderService.completeOrder(order);
        chefService.addOrder(order);
    }

    public Set<UUID> listCompletedOrders() {
        return chefService.getOrders().stream().map(Order::getId).collect(Collectors.toSet());
    }

    public void prepareOrder(UUID orderId) throws OrderNotFoundException {
        Order order = getNotPreparedOrder(orderId);
        chefService.prepareOrder(order);
        deliveryService.addOrder(order);
    }

    public Set<UUID> listPreparedOrders() {
        return deliveryService.getOrders().stream().map(Order::getId).collect(Collectors.toSet());
    }

    public Order deliverOrder(UUID orderId) throws OrderNotFoundException {
        Order order = getNotDeliveredOrder(orderId);
        deliveryService.deliverOrder(order);
        return order;
    }
}
