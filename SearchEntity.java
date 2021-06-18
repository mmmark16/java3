package com.company;

public class SearchEntity {
    private Entity entity;
    private double distance;

    public SearchEntity(Entity entity, double distance) {
        this.entity = entity;
        this.distance = distance;
    }

    public Entity getEntity() {
        return entity;
    }

    public double getDistance() {
        return distance;
    }
}