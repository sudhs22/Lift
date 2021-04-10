package com.sudhs.process;

import com.sudhs.builder.BuildingBuilder;
import com.sudhs.entities.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LiftProcess extends Thread{
    private static final Logger logger = LogManager.getLogger(LiftProcess.class);
    private static final int NO_OF_FLOORS = Building.getTopFloor() - Building.getInitialFloor();
    private static final int DEFAULT_WAIT_TIME = 3000;

    Building building = BuildingBuilder.getFullyConstructedBuilding();
    private final Lift lift = building.getLift();
    private final LiftPanel liftPanel = lift.getLiftPanel();

    @Override
    public void run() {
        while(! lift.isLocked()) {
            List<Boolean> allPressedButtons = getAllButtonsPressed(building, liftPanel);
            int nearestFloor = getNearestFloor(allPressedButtons);
            if (isValid(nearestFloor))
                moveLift(nearestFloor, lift.getCurrentFloor());

            waitForSeconds(DEFAULT_WAIT_TIME);
        }
    }

    private boolean isValid(int nearestFloor) {
        if(nearestFloor > NO_OF_FLOORS) {
            logger.debug("Flipping Direction if no floors pressed in current direction.");
            if(lift.getDirection().equals(Direction.UP))
                lift.setDirection(Direction.DOWN);
            else
                lift.setDirection(Direction.UP);
            return false;
        }
        return true;
    }

    private void moveLift(int nearestFloor, int liftCurrentFloor) {
        logger.info("Current floor :: " + liftCurrentFloor + "  DestinationFloor:" + nearestFloor + "   Direction :: " + lift.getDirection().name());

        while(liftCurrentFloor != nearestFloor) {
            logger.info("Lift is at :: " + liftCurrentFloor);
            waitForSeconds(DEFAULT_WAIT_TIME);
            liftCurrentFloor = (lift.getDirection().equals(Direction.DOWN)) ? --liftCurrentFloor : ++liftCurrentFloor;
            lift.setCurrentFloor(liftCurrentFloor);
        }
        logger.info("Lift is at :: " + liftCurrentFloor);
        lift.getLiftPanel().getFloorNumberButtons().get(liftCurrentFloor).setSelected(false);
        building.getFloors().get(liftCurrentFloor).getFloorPanel().getDownButton().setPressed(false);
        logger.info("Doors opening");
        lift.setDoorOpen(true);
        waitForSeconds(7000);
        lift.setDoorOpen(false);
        logger.info("Door closing");
    }


    private int getNearestFloor(List<Boolean> allPressedButtons) {
        int nearestFloor = NO_OF_FLOORS + 1;
        int nearestUpFloor = nearestFloor;
        int nearestDownFloor = nearestFloor;
        int liftCurrentFloor = lift.getCurrentFloor();

        for(int i = liftCurrentFloor; i <= Building.getTopFloor(); i++){
            if(Boolean.TRUE.equals(allPressedButtons.get(i))){
                nearestUpFloor = i;
                break;
            }
        }
        for(int i = liftCurrentFloor; i >= Building.getInitialFloor(); i--){
            if(Boolean.TRUE.equals(allPressedButtons.get(i))){
                nearestDownFloor = i;
                break;
            }
        }

        switch (lift.getDirection()) {
            case DOWN:
                nearestFloor = nearestDownFloor;
                break;
            case UP:
                nearestFloor = nearestUpFloor;
                break;
            case STATIONARY:
                if (liftCurrentFloor - nearestDownFloor > nearestUpFloor - liftCurrentFloor){
                    lift.setDirection(Direction.UP);
                    nearestFloor = nearestUpFloor;
                } else {
                    lift.setDirection(Direction.DOWN);
                    nearestFloor = nearestDownFloor;
                }
                break;
            default:
                lift.setDirection(Direction.STATIONARY);
                break;

        }
        return nearestFloor;
    }

    private List<Boolean> getAllButtonsPressed(Building building, LiftPanel liftPanel) {
        List<Boolean> buildingButtons = new ArrayList<>(NO_OF_FLOORS);
        for(LiftButton button : liftPanel.getFloorNumberButtons())
                buildingButtons.add(button.getFloorNumber(), button.isSelected());

        for(Floor floor : building.getFloors()) {
            if(floor.getFloorPanel().getDownButton().isPressed() || floor.getFloorPanel().getUpButton().isPressed())
                buildingButtons.add(floor.getFloorNumber(), true);
        }
        return buildingButtons;
    }

    public static void waitForSeconds(long milliseconds) {
        try{Thread.sleep(milliseconds);}catch (Exception e){e.printStackTrace();}
    }
}
