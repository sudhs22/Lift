package com.sudhs.process;

import com.sudhs.builder.BuildingBuilder;
import com.sudhs.entities.Building;
import com.sudhs.entities.Direction;
import com.sudhs.entities.Lift;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import static com.sudhs.process.LiftProcess.waitForSeconds;

public class LiftProcessTest {
    private static final Logger logger = LogManager.getLogger(LiftProcessTest.class);
    Building building = BuildingBuilder.getFullyConstructedBuilding();

    @Test
    public void testLiftFunction(){
        {
            Lift lift = building.getLift();

            logger.info("Test class -- People Inside Lift pressed 3rd and 1st floor");
            logger.info("Test class -- People at floor no 4 calls for the Lift");
            lift.getLiftPanel().getFloorNumberButtons().get(3).setSelected(true);
            lift.getLiftPanel().getFloorNumberButtons().get(1).setSelected(true);
            building.getFloors().get(4).getFloorPanel().getDownButton().setPressed(true);

            logger.info("Test class -- Lift will be idle for 1 minute");

            waitForSeconds(60000);
            for(int i = Building.getInitialFloor(); i <= Building.getTopFloor(); i++) {
                Assert.assertTrue(!building.getFloors().get(i).getFloorPanel().getDownButton().isPressed());
                Assert.assertTrue(!building.getFloors().get(i).getFloorPanel().getUpButton().isPressed());
                Assert.assertTrue(!lift.getLiftPanel().getFloorNumberButtons().get(i).isSelected());
                Assert.assertTrue(lift.getCurrentFloor().equals(4));
            }
            waitForSeconds(1000);
            logger.info("Test class -- Person standing in Ground floor calls for the lift");
            building.getFloors().get(0).getFloorPanel().getDownButton().setPressed(true);
            while(! lift.isDoorOpen() && lift.getCurrentFloor() != 0)
                waitForSeconds(2000);
            Assert.assertTrue(lift.getCurrentFloor().equals(0));

            logger.info("Test class -- Person gets into the Lift in Ground floor and presses 4th floor");
            lift.getLiftPanel().getFloorNumberButtons().get(4).setSelected(true);
            waitForSeconds(10000);
            while(!lift.getDirection().equals(Direction.STATIONARY) && !lift.isDoorOpen())
                waitForSeconds(2000);
            Assert.assertTrue(lift.getCurrentFloor().equals(4));
            lift.setLocked(true);
        }
    }
}
