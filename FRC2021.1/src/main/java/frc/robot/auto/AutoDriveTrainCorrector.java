package frc.robot.auto;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

public class AutoDriveTrainCorrector {

    public class AutoDriveTrainValue
    {
        public double time;
        public double velocity;

        public AutoDriveTrainValue(double time, double value)
        {
            this.time = time;
            this.velocity = value;
        }
    }

    //double correctionDelay; may want to add to prevent oscillation, also in constructor

    private double targetVelocity;
    private double correctionThreshold;
    private double correction;
    private int cycleReachback;
    
    private double speed;

    private ArrayList<AutoDriveTrainValue> velocities = new ArrayList<AutoDriveTrainValue>();
    
    public AutoDriveTrainCorrector(double startSpeed, double targetValue, double correctionRange, double correction, int cycleReachback)
    {
        speed = startSpeed;
        this.targetVelocity = targetValue;
        this.correctionThreshold = correctionRange;
        this.correction = correction;
        this.cycleReachback = cycleReachback;
    }

    public double correctSpeed(double currentVelocity) //Should be called every cycle
    {
        velocities.add(new AutoDriveTrainValue(Timer.getFPGATimestamp(), currentVelocity)); 

        double referenceVelocity = getPreviousVelocity();
        double velocityDifference = targetVelocity-referenceVelocity;
        if (Math.abs(velocityDifference) >= correctionThreshold)
        {
            if (velocityDifference > 0)
                speed += correction;
            else
                speed -= correction; // May want to make asymptote thingy
        }
        return speed;
    }

    // Could guess future stuff by taking previous

    private double getPreviousVelocity()
    {
        int wantedIndex = velocities.size()-cycleReachback-1;
        if(wantedIndex >= 0)
            return velocities.get(wantedIndex).velocity;

        return velocities.get(0).velocity;
    }
}
