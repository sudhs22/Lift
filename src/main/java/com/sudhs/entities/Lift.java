package com.sudhs.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lift {
    private Integer currentFloor;
    private LiftPanel liftPanel;
    private boolean doorOpen;
    private Direction direction;
    private boolean locked;
}