package com.sudhs.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PanelButton {
    Direction direction;
    boolean pressed;

    public PanelButton(Direction direction){
        this.direction = direction;
    }

}