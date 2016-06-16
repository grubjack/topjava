package ru.javawebinar.topjava.model;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class BaseEntity {
    protected Integer id;

    public BaseEntity() {
    }

    protected BaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }
}