package com.sudhs.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LiftButton {
    Integer floorNumber;
    boolean isSelected;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
