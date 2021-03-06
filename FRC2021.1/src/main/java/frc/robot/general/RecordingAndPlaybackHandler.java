package frc.robot.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

public class RecordingAndPlaybackHandler {

    private RobotHandler robotHandler;

    public RecordingAndPlaybackHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        recorder = new Recorder();
        player = new Player(robotHandler);
    }

    public Map<String, Recording> recordings = new HashMap<String, Recording>();

    public Recorder recorder;

    public Player player;

    public String selectedRecording = "Recording 0";

    private int recordingId = 0;

    public void teleopPeriodic()
    {
        if (robotHandler.inputHandler.isEitherOrDriveJoystickButton(Constants.Buttons.TOGGLE_RECORDING))
        {
            if (robotHandler.stateHandler.lastRecordingToggleTime + 1 < Timer.getFPGATimestamp())
            {
                robotHandler.stateHandler.toggleRecording();
                if (!robotHandler.stateHandler.recording) //Stop recording
                {
                    String name = recorder.name;
                    Recording recording = recorder.endRecording();
                    recordings.put(name, recording);
                }
                else // Start recording
                {
                    recorder.newRecording("Recording " + String.valueOf(recordingId));
                    recordingId++;
                }
            }
        }
        if (robotHandler.inputHandler.isEitherOrDriveJoystickButton(Constants.Buttons.TOGGLE_PLAYBACK))
        {
            if (robotHandler.stateHandler.lastPlayerToggleTime + 1 < Timer.getFPGATimestamp())
            {
                robotHandler.stateHandler.togglePlayer();
                if (!robotHandler.stateHandler.playing) //Stop playing
                {
                    player.stop();
                }
                else // Start playing
                {
                    player.play(recordings.get(selectedRecording));
                }
            }
        }
    }
    public MotorPeriod sendMotorPeriodValues()
    {
        if (recorder.isRecording)
        {
            
        }
    }

    public class Recording
    {
        public List<MotorPeriod> recording = new ArrayList<MotorPeriod>();
    }
    public class Recorder
    {
        public String name;
        public Recording recording;

        public boolean isRecording = false;

        private double lastLeftValue;
        private double lastRightValue;

        public void newRecording(String name)
        {
            this.name = name;
            recording = new Recording();
            isRecording = true;
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
            isRecording = false;
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
        private double startTime;

        public boolean isPlaying = false;
        private Recording recording;

        public Player(RobotHandler robotHandler)
        {
            this.robotHandler = robotHandler;
        }
        public void play(Recording recording)
        {
            startTime = Timer.getFPGATimestamp();
            this.recording = recording;
            isPlaying = true;
        }
        public void run()
        {
            if (!isPlaying) return;
            MotorPeriod motorPeriod = findSuitableMotorPeriod();
            robotHandler.driveHandler.differentialDrive.tankDrive(motorPeriod.speedLeft, motorPeriod.speedLeft);
        }
        public void stop()
        {
            isPlaying = false;
        }
        private MotorPeriod findSuitableMotorPeriod()
        {
            double time = Timer.getFPGATimestamp() - startTime;
            int index = 0;
            while(recording.recording.get(index).endTime < time)
            {
                if (!(recording.recording.size() < index+1))
                    index++;
                else
                    return new MotorPeriod(0, 0, 0);
            }
            return recording.recording.get(index-1);
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
