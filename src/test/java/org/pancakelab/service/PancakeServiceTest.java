package org.pancakelab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.pancakelab.controller.OrderController;
import org.pancakelab.exceptions.InvalidIngredientInputException;
import org.pancakelab.model.Order;
import org.pancakelab.model.Pancake;

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
    public void setup() throws InvalidIngredientInputException {
        darkChocolatePancake = orderController.makePancake(Set.of("DARKCHOCOLATE"));
        milkChocolatePancake = orderController.makePancake(Set.of("MILKCHOCOLATE"));
        milkChocolateHazelnutPancake = orderController.makePancake(Set.of("MILKCHOCOLATE", "HAZELNUT"));
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
        orderController.removePancakes(Set.of("DARKCHOCOLATE"), order.getId(), 2);
        orderController.removePancakes(Set.of("MILKCHOCOLATE"), order.getId(), 3);
        orderController.removePancakes(Set.of("MILKCHOCOLATE", "HAZELNUT"), order.getId(), 1);

        // verify
        List<Pancake> pancakesLeftInOrder = orderController.getNotCompletedOrder(order.getId()).getPancakes();

        assertEquals(List.of(darkChocolatePancake,
                milkChocolateHazelnutPancake,
                milkChocolateHazelnutPancake), pancakesLeftInOrder);

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
        order = orderController.createOrder(10, 20);
        addPancakes();

        // exercise
        orderController.cancelOrder(order.getId());

        // verify
        Set<UUID> completedOrders = orderController.listCompletedOrders();
        assertFalse(completedOrders.contains(order.getId()));

        Set<UUID> preparedOrders = orderController.listPreparedOrders();
        assertFalse(preparedOrders.contains(order.getId()));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(70)
    public void GivenInvalidIngredient_WhenAddingPancake_ThenExceptionIsThrown_Test() throws Exception {
        // setup
        order = orderController.createOrder(10, 20);
        Set<String> invalidIngredients = Set.of("MUSTARD");

        // exercise
        assertThrows(InvalidIngredientInputException.class,
                () -> orderController.addPancake(invalidIngredients, order.getId(), 3));

        // verify
        List<Pancake> pancakesInOrder = orderController.getNotCompletedOrder(order.getId()).getPancakes();
        Set<String> pancakeNames = pancakesInOrder.stream().map(Pancake::getDescription).collect(Collectors.toSet());
        assertFalse(pancakeNames.contains("MUSTARD"));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(80)
    public void GivenNoIngredients_WhenAddingPancake_ThenExceptionIsThrown_Test() throws Exception {
        // setup
        order = orderController.createOrder(10, 20);
        Set<String> invalidIngredients = Set.of();

        // exercise
        assertThrows(InvalidIngredientInputException.class,
                () -> orderController.addPancake(invalidIngredients, order.getId(), 3));

        // verify

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(90)
    public void GivenDarkchocolatePancake_ThenCorrectDescription_Test() {
        // setup

        // exercise

        // verify
        assertEquals("Dark Chocolate Pancake", darkChocolatePancake.getDescription());

        // tear down

    }

    private void addPancakes() throws Exception {
        orderController.addPancake(Set.of("DARKCHOCOLATE"), order.getId(), 3);
        orderController.addPancake(Set.of("MILKCHOCOLATE"), order.getId(), 3);
        orderController.addPancake(Set.of("MILKCHOCOLATE", "HAZELNUT"), order.getId(), 3);
    }
}
