package com.sudhs.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LiftPanel {
    List<LiftButton> floorNumberButtons = new ArrayList<>();

    public LiftPanel(List<Integer> floorNumbers) {
        floorNumbers.forEach(floorNumber -> floorNumberButtons.add(new LiftButton(floorNumber, false)));
    }

//    public boolean isSelected(int floorNumber){
//        for(Integer floorNum : floorNumberButtons) {
//
//        }
//    }
}
