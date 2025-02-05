package org.pancakelab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.pancakelab.controller.OrderController;
import org.pancakelab.exceptions.OrderNotFoundException;
import org.pancakelab.model.Order;
import org.pancakelab.model.pancake.Pancake;
import org.pancakelab.model.pancake.PancakeDirector;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PancakeServiceTest {
    private OrderService orderService = new OrderService();
    private ChefService chefService = new ChefService();
    private DeliveryService deliveryService = new DeliveryService();
    private OrderController orderController = new OrderController(orderService, chefService, deliveryService);
    private Order order = null;
    private Pancake darkChocolatePancake, milkChocolatePancake, milkChocolateHazelnutPancake;

    @BeforeAll
    public void setup() {
        PancakeDirector pancakeDirector = new PancakeDirector();
        darkChocolatePancake = pancakeDirector.makeDarkChocolatePancake();
        milkChocolatePancake = pancakeDirector.makeMilkChocolatePancake();
        milkChocolateHazelnutPancake = pancakeDirector.makeMilkChocolateHazelnutPancake();
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    public void GivenOrderDoesNotExist_WhenCreatingOrder_ThenOrderCreatedWithCorrectData_Test() {
        // setup

        // exercise
        order = orderController.createOrder(10, 20);

        assertEquals(10, order.getAddress().getBuilding());
        assertEquals(20, order.getAddress().getRoom());

        // verify

        // tear down
    }

    @Test
    public void GivenDarkchocolatePancake_ThenCorrectDescription_Test() {
        assertEquals("Dark Chocolate Pancake", darkChocolatePancake.getDescription());
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    public void GivenOrderExists_WhenAddingPancakes_ThenCorrectNumberOfPancakesAdded_Test() throws Exception {
        // setup

        // exercise
        addPancakes();

        // verify
        List<Pancake> actualPancakes = orderController.getNotCompletedOrder(order.getId()).getPancakes();
        Map<Pancake, Integer> actualPancakesMap = new HashMap<>();
        for (Pancake pancake : actualPancakes) {
            actualPancakesMap.putIfAbsent(pancake, 0);
            actualPancakesMap.put(pancake, actualPancakesMap.get(pancake) + 1);
        }

        assertEquals(Map.of(darkChocolatePancake, 3, milkChocolateHazelnutPancake, 3, milkChocolatePancake, 3),
                actualPancakesMap);

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    public void GivenPancakesExists_WhenRemovingPancakes_ThenCorrectNumberOfPancakesRemoved_Test() throws Exception {
        // setup

        // exercise
        orderController.removePancakes("DARK_CHOCOLATE_PANCAKE", order.getId(), 2);
        orderController.removePancakes("MILK_CHOCOLATE_PANCAKE", order.getId(), 3);
        orderController.removePancakes("MILK_CHOCOLATE_HAZELNUTS_PANCAKE", order.getId(), 1);

        // verify
        List<Pancake> pancakesInOrder = orderController.getNotCompletedOrder(order.getId()).getPancakes();

        assertEquals(List.of(darkChocolatePancake,
                milkChocolateHazelnutPancake,
                milkChocolateHazelnutPancake), pancakesInOrder);

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(40)
    public void GivenOrderExists_WhenCompletingOrder_ThenOrderCompleted_Test() throws Exception {
        // setup

        // exercise
        orderController.completeOrder(order.getId());

        // verify
        Set<UUID> completedOrdersOrders = orderController.listCompletedOrders();
        assertTrue(completedOrdersOrders.contains(order.getId()));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(50)
    public void GivenOrderExists_WhenPreparingOrder_ThenOrderPrepared_Test() throws Exception {
        // setup

        // exercise
        orderController.prepareOrder(order.getId());

        // verify
        Set<UUID> completedOrders = orderController.listCompletedOrders();
        assertFalse(completedOrders.contains(order.getId()));

        Set<UUID> preparedOrders = orderController.listPreparedOrders();
        assertTrue(preparedOrders.contains(order.getId()));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(60)
    public void GivenOrderExists_WhenDeliveringOrder_ThenCorrectOrderReturnedAndOrderRemovedFromTheDatabase_Test()
            throws Exception {
        // setup

        // exercise
        Order deliveredOrder = orderController.deliverOrder(order.getId());

        // verify
        Set<UUID> completedOrders = orderController.listCompletedOrders();
        assertFalse(completedOrders.contains(order.getId()));

        Set<UUID> preparedOrders = orderController.listPreparedOrders();
        assertFalse(preparedOrders.contains(order.getId()));

        assertEquals(order.getId(), deliveredOrder.getId());
        assertEquals(order.getPancakes(), deliveredOrder.getPancakes());

        // tear down
        order = null;
    }

    @Test
    @org.junit.jupiter.api.Order(70)
    public void GivenOrderExists_WhenCancellingOrder_ThenOrderRemoved_Test() throws Exception {
        // setup
        order = orderService.createOrder(10, 20);
        addPancakes();

        // exercise
        orderService.cancelOrder(order.getId());

        // verify
        Set<UUID> completedOrders = orderController.listCompletedOrders();
        assertFalse(completedOrders.contains(order.getId()));

        Set<UUID> preparedOrders = orderController.listPreparedOrders();
        assertFalse(preparedOrders.contains(order.getId()));

        // tear down
    }

    private void addPancakes() throws OrderNotFoundException {
        orderController.addPancake("DARK_CHOCOLATE_PANCAKE", order.getId(), 3);
        orderController.addPancake("MILK_CHOCOLATE_PANCAKE", order.getId(), 3);
        orderController.addPancake("MILK_CHOCOLATE_HAZELNUTS_PANCAKE", order.getId(), 3);
    }
}
