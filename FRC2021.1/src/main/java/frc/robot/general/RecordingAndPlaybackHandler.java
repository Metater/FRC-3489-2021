package frc.robot.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.wpilibj.Timer;

public class RecordingAndPlaybackHandler {

    public Map<String, Recording> recordings = new HashMap<String, Recording>();


    public class Recording
    {
        public List<MotorPeriod> recording = new ArrayList<MotorPeriod>();
    }
    public class Recorder
    {
        public String name;
        public Recording recording;

        private double lastLeftValue;
        private double lastRightValue;

        public void newRecording(String name)
        {
            this.name = name;
            recording = new Recording();
        }
        public void tryAddMotorPeriod(double leftValue, double rightValue)
        {
            if ((lastLeftValue != leftValue) || (lastRightValue != rightValue))
            {
                MotorPeriod motorPeriod = new MotorPeriod(lastLeftValue, lastRightValue, Timer.getFPGATimestamp());
                addMotorPeriod(motorPeriod);
                lastLeftValue = leftValue;
                lastRightValue = rightValue;
            }
        }
        public Recording endRecording()
        {
            MotorPeriod motorPeriod = new MotorPeriod(lastLeftValue, lastRightValue, Timer.getFPGATimestamp());
            addMotorPeriod(motorPeriod);
            return recording;
        }
        private void addMotorPeriod(MotorPeriod motorPeriod)
        {
            recording.recording.add(motorPeriod);
        }
    }
    public class Player
    {
        private RobotHandler robotHandler;
        public Player(RobotHandler robotHandler)
        {
            this.robotHandler = robotHandler;
        }
    }
    public class MotorPeriod
    {
        public double speedLeft;
        public double speedRight;
        public double endTime;

        public MotorPeriod(double speedLeft, double speedRight, double endTime)
        {
            this.speedLeft = speedLeft;
            this.speedRight = speedRight;
            this.endTime = endTime;
        }
    }
}
