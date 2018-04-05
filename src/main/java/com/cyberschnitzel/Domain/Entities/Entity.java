package com.cyberschnitzel.Domain.Entities;

public class Entity {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity {" +
                "id=" + id +
                '}';
    }
}