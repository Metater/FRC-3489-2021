package frc.robot.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordingAndPlaybackHandler {

    public Map<String, Recording> recordings = new HashMap<String, Recording>();


    public class Recording
    {
        public List<MotorPeriod> recording = new ArrayList<MotorPeriod>();
    }
    public class Recorder
    {
        
    }
    public class Player
    {

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
