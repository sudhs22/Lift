package com.sudhs.entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Building {
    private static final Logger logger = LogManager.getLogger(Building.class);

    private static Building building;
    private List<Floor> floors = new ArrayList<>();
    private Lift lift;
    private static final Integer INITIAL_FLOOR = 0;
    private static final Integer TOP_FLOOR = 5;

    public static Integer getInitialFloor(){
        return INITIAL_FLOOR;
    }

    public static Integer getTopFloor(){
        return TOP_FLOOR;
    }

}
