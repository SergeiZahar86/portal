package com.indas.portal.entities;

import java.io.Serializable;

public class CarId implements Serializable {

    private int id;
    private String partId;

    public CarId(int id, String partId) {
        this.id = id;
        this.partId = partId;
    }

    public CarId() {

    }
}
