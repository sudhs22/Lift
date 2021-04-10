package com.sudhs.builder;

import com.sudhs.entities.*;
import com.sudhs.process.LiftProcess;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class BuildingBuilder {
    private static final Logger logger = LogManager.getLogger(BuildingBuilder.class);

    private static Building building;
    private BuildingBuilder(){}

    public static Building getFullyConstructedBuilding() {
        if(building == null) {
            building = new Building();
            for (int floorNumber = Building.getInitialFloor(); floorNumber <= Building.getTopFloor(); floorNumber++)
                building.getFloors().add(new Floor(floorNumber));


            Lift lift = new Lift();
            lift.setCurrentFloor(0);
            lift.setDoorOpen(false);
            lift.setDirection(Direction.STATIONARY);
            List<Integer> floorNumbers = building.getFloors().stream().map(Floor::getFloorNumber).collect(Collectors.toList());
            lift.setLiftPanel(new LiftPanel(floorNumbers));
            lift.setLocked(false);
            building.setLift(lift);
            logger.info("The building is now set people. Start using it now !!!");

            LiftProcess liftProcess = new LiftProcess();
            liftProcess.start();
        }
        return building;
    }
}
