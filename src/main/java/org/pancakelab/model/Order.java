package org.pancakelab.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.pancakelab.exceptions.NotEnoughPancakesException;
import org.pancakelab.model.pancake.Pancake;

public class Order {
    private final UUID id;
    private final Address address;
    private final List<Pancake> pancakes;

    public Order(int building, int room) {
        this.id = UUID.randomUUID();
        this.address = new Address(building, room);
        this.pancakes = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public synchronized Pancake addPancake(Pancake pancake) {
        pancakes.add(pancake);
        return pancake;
    }

    public synchronized void removePancakes(Pancake toRemove, int count) throws NotEnoughPancakesException {
        if (pancakes.stream().filter(p -> p.equals(toRemove)).count() < count) {
            throw new NotEnoughPancakesException();
        }
        for (Iterator<Pancake> iter = pancakes.iterator(); iter.hasNext() && count != 0;) {
            Pancake pancake = iter.next();
            if (pancake.equals(toRemove)) {
                iter.remove();
                count--;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public List<Pancake> getPancakes() {
        return pancakes;
    }

}
