package frc.robot.auto.autos;

import frc.robot.auto.AutoInstruction;
import frc.robot.auto.AutoMaker;
import frc.robot.general.AutoHandler;

public class AutoSlalom extends AutoMaker {
    
    public AutoSlalom(AutoHandler autoHandler)
    {
        loadAutoMaker(autoHandler);
    }

    public AutoInstruction[] autoSlalom = {
        // Clear start box, and move to left of main line
        drive(0.8, 3000),
        waitFor(1),
        turnLeft(0.7, 700),
        drive(0.8, 6800),
        waitFor(2),
        turnRight(0.8, 630),
        waitFor(1),
        // Go zoom to other end
        drive(0.9, 19400),
        waitFor(2),
        turnRight(0.8, 750),
        waitFor(1),
        drive(0.8, 7000),
        waitFor(2),
        // Do fancy and very hard loop
        tank(0.335, 0.68, 13000),
        tank(0.3, 0.67, 10000),
        waitFor(2),
        drive(0.8, 6000),
        waitFor(1),
        turnLeft(0.8, 750),
        waitFor(1),
        drive(0.8, 6000),
        waitFor(1),
        turnRight(0.8, 900),
        waitFor(1),
        drive(0.9, 19400),
        waitFor(1),
        stop()
    };
}
