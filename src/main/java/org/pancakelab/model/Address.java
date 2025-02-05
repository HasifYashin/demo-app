package org.pancakelab.model;

public class Address {
    private final int building;
    private final int room;

    public Address(int building, int room) {
        this.building = building;
        this.room = room;
    }

    public int getBuilding() {
        return building;
    }

    public int getRoom() {
        return room;
    }
}
