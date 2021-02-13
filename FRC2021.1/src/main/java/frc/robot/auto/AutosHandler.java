package frc.robot.auto;

import java.util.HashMap;
import java.util.Map;

import frc.robot.general.AutoHandler;

public class AutosHandler extends AutoMaker {

    public enum AutoType
    {
        Joe,
        Slalom,
        TheChugLife,
        Bounce,
        Barrel,
        HyperPathA,
        HyperPathB
    }

    private AutoHandler autoHandler;
    public Map<AutoType, AutoInstruction[]> autos = new HashMap<AutoType, AutoInstruction[]>();
    
    public AutosHandler(AutoHandler autoHandler)
    {
        this.autoHandler = autoHandler;
        loadAutoMaker(autoHandler);
        populateAutoList();
    }

    private void populateAutoList()
    {
        autos.put(AutoType.Slalom, autoSlalom);
        autos.put(AutoType.Joe, autoJoe);
        autos.put(AutoType.TheChugLife, autoTheChugLife);
        autos.put(AutoType.Bounce, autoBounce);
        autos.put(AutoType.Barrel, autoBarrel);
        autos.put(AutoType.HyperPathA, autoHyperPathA);
        autos.put(AutoType.HyperPathB, autoHyperPathB);
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
    public AutoInstruction[] autoJoe = {
        waitFor(2),
        drive(0.8, 1000),
        waitFor(1),
        turnLeft(0.6, 3000),
        waitFor(1),
        turnRight(0.6, 3000),
        waitFor(1),
        drive(-0.6, 1000),
        waitFor(2),
        drive(0.6, 1000),
        waitFor(1),
        turnLeft(0.6, 3000),
        waitFor(1),
        turnRight(0.6, 3000),
        waitFor(1),
        drive(-0.6, 1000),
        stop()
    };
    public AutoInstruction[] autoTheChugLife = {
        waitFor(4),
        tank(1.0, 0.4, 750),
        waitFor(1),
        tank(0.4, 1.0, 750),
        stop()
    };
    
    public AutoInstruction[] autoBounce = {
        tank(0.3, 0.67, 5000), //curved left turn
        waitFor(1),
        drive(0.8, 6000),
        waitFor(1),
        tank(-0.8, -0.1, 10000),//funny curved backwards turn
        tank(-0.6, -0.2, 15000),//funnier curved backwards turn
        waitFor(1),
        drive(-0.8, 15000),
        waitFor(1),
        drive(0.8, 20000),
        waitFor(1),
        tank(0.3, 0.8, 10000),
        waitFor(1),
        drive(0.8, 10000),
        tank(-0.8, -0.3, 5000),
        stop()

    };

    public AutoInstruction[] autoBarrel = {
        drive(0.8, 10000),
        waitFor(1),
        tank(0.8, 0.3, 10000),
        waitFor(1),
        drive(0.8, 5000),
        waitFor(1),
        tank(0.8, 0.3, 3000),
        waitFor(1),
        drive(0.8, 10000),
        waitFor(1),
        tank(0.3, 0.8, 10000),
        waitFor(1),
        drive(0.8, 5000),
        waitFor(1),
        drive(0.8, 5000),
        waitFor(1),
        tank(0.8, 0.3, 5000),
        waitFor(1),
        drive(0.6, 250),
        drive(0.8, 500),
        drive(1.0, 20000),
        stop()
    };

    public AutoInstruction[] autoHyperPathA = {
        drive(0.6, 3000),
        //add zucc and tank here
        waitFor(1),
        turnRight(0.6, 2000),
        waitFor(1),
        drive(0.6, 2000),
        //add zucc and tank here
        waitFor(1),
        turnLeft(0.6, 5000),
        waitFor(1),
        drive(0.8, 4000),
        //add zucc and tank here
        waitFor(1),
        turnRight(0.6, 2000),
        waitFor(1),
        drive(0.6, 250),
        drive(0.8, 500),
        drive(1, 8000),

    };

    public AutoInstruction[] autoHyperPathB = {
        drive(0.6, 3000),
        //add zucc and tank here
        waitFor(1),
        turnRight(0.6, 2000),
        waitFor(1),
        drive(0.6, 2000),
        //add zucc and tank here
        waitFor(1),
        turnLeft(0.6, 5000),
        waitFor(1),
        drive(0.8, 4000),
        //add zucc and tank here
        waitFor(1),
        turnRight(0.6, 2000),
        waitFor(1),
        drive(0.6, 250),
        drive(0.8, 500),
        drive(1, 8000),

    };
}
