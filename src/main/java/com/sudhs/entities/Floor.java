package com.sudhs.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Floor {
    private final Integer floorNumber;
    private FloorPanel floorPanel;

    public Floor(Integer floorNumber) {
        this(floorNumber, new FloorPanel());
    }

    public Floor (Integer floorNumber, FloorPanel floorPanel) {
        this.floorNumber = floorNumber;
        this.floorPanel = floorPanel;
    }

}


