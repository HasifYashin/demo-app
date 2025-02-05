package org.pancakelab.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.pancakelab.exceptions.NotEnoughPancakesException;
import org.pancakelab.exceptions.OrderNotFoundException;
import org.pancakelab.model.Order;
import org.pancakelab.model.pancake.Pancake;
import org.pancakelab.model.pancake.PancakeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PancakeServiceTest {
    private OrderService orderService = new OrderService();
    private Order order = null;
    private Pancake darkChocolatePancake, milkChocolatePancake, milkChocolateHazelnutPancake;

    @BeforeAll
    public void setup() {
        PancakeBuilder builder = new PancakeBuilder();
        darkChocolatePancake = builder.addDarkChocolate().build();
        builder.reset();
        milkChocolatePancake = builder.addMilkChocolate().build();
        builder.reset();
        milkChocolateHazelnutPancake = builder.addMilkChocolate().addHazelNut().build();
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    public void GivenOrderDoesNotExist_WhenCreatingOrder_ThenOrderCreatedWithCorrectData_Test() {
        // setup

        // exercise
        order = orderService.createOrder(10, 20);

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
        List<Pancake> actualPancakes = orderService.getOrder(order.getId()).getPancakes();
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
        orderService.removePancakes(darkChocolatePancake, order.getId(), 2);
        orderService.removePancakes(milkChocolatePancake, order.getId(), 3);
        orderService.removePancakes(milkChocolateHazelnutPancake, order.getId(), 1);

        // verify
        List<Pancake> pancakesInOrder = orderService.getPancakesInOrder(order.getId());

        assertEquals(List.of(darkChocolatePancake,
                milkChocolateHazelnutPancake,
                milkChocolateHazelnutPancake), pancakesInOrder);

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(40)
    public void GivenOrderExists_WhenCompletingOrder_ThenOrderCompleted_Test() {
        // setup

        // exercise
        orderService.completeOrder(order.getId());

        // verify
        Set<UUID> completedOrdersOrders = orderService.listCompletedOrders();
        assertTrue(completedOrdersOrders.contains(order.getId()));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(50)
    public void GivenOrderExists_WhenPreparingOrder_ThenOrderPrepared_Test() {
        // setup

        // exercise
        orderService.prepareOrder(order.getId());

        // verify
        Set<UUID> completedOrders = orderService.listCompletedOrders();
        assertFalse(completedOrders.contains(order.getId()));

        Set<UUID> preparedOrders = orderService.listPreparedOrders();
        assertTrue(preparedOrders.contains(order.getId()));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(60)
    public void GivenOrderExists_WhenDeliveringOrder_ThenCorrectOrderReturnedAndOrderRemovedFromTheDatabase_Test() {
        // setup
        List<String> pancakesToDeliver = orderService.viewOrder(order.getId());

        // exercise
        Object[] deliveredOrder = orderService.deliverOrder(order.getId());

        // verify
        Set<UUID> completedOrders = orderService.listCompletedOrders();
        assertFalse(completedOrders.contains(order.getId()));

        Set<UUID> preparedOrders = orderService.listPreparedOrders();
        assertFalse(preparedOrders.contains(order.getId()));

        List<String> ordersPancakes = orderService.viewOrder(order.getId());

        assertEquals(List.of(), ordersPancakes);
        assertEquals(order.getId(), ((Order) deliveredOrder[0]).getId());
        assertEquals(pancakesToDeliver, (List<String>) deliveredOrder[1]);

        // tear down
        order = null;
    }

    @Test
    @org.junit.jupiter.api.Order(70)
    public void GivenOrderExists_WhenCancellingOrder_ThenOrderAndPancakesRemoved_Test() throws Exception {
        // setup
        order = orderService.createOrder(10, 20);
        addPancakes();

        // exercise
        orderService.cancelOrder(order.getId());

        // verify
        Set<UUID> completedOrders = orderService.listCompletedOrders();
        assertFalse(completedOrders.contains(order.getId()));

        Set<UUID> preparedOrders = orderService.listPreparedOrders();
        assertFalse(preparedOrders.contains(order.getId()));

        List<String> ordersPancakes = orderService.viewOrder(order.getId());

        assertEquals(List.of(), ordersPancakes);

        // tear down
    }

    private void addPancakes() throws OrderNotFoundException {
        orderService.addDarkChocolatePancake(order.getId(), 3);
        orderService.addMilkChocolatePancake(order.getId(), 3);
        orderService.addMilkChocolateHazelnutsPancake(order.getId(), 3);
    }
}
