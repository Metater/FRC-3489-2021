package frc.robot.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

// Redbull Replay
public class RecordingAndPlaybackHandler {

    private RobotHandler robotHandler;

    public Recorder recorder;
    public Player player;


    public List<Recording> recordings = new ArrayList<Recording>();
    public int selectedRecording = -1;

    public RecordingAndPlaybackHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        recorder = new Recorder();
        player = new Player(robotHandler);
    }

    public void robotInit()
    {

    }

    public void teleopPeriodic()
    {
        if (robotHandler.inputHandler.isEitherOrDriveJoystickButton(Constants.Buttons.TOGGLE_RECORDING))
        {
            if (robotHandler.stateHandler.lastRecordingToggleTime + 1 < Timer.getFPGATimestamp())
            {
                if (!player.isPlaying)
                    toggleRecorder();
            }
        }
        if (robotHandler.inputHandler.isEitherOrDriveJoystickButton(Constants.Buttons.TOGGLE_PLAYBACK))
        {
            if (robotHandler.stateHandler.lastPlayerToggleTime + 1 < Timer.getFPGATimestamp())
            {
                if (!recorder.isRecording)
                    togglePlayer();
            }
        }
        if (recorder.isRecording)
        {

        }
        else if (player.isPlaying)
        {

        }
    }
    private void toggleRecorder()
    {
        robotHandler.stateHandler.toggleRecording();
        if (!robotHandler.stateHandler.isRecording) //Stop recording
        {
            Recording recording = recorder.endRecording();
            recordings.put(recording);
        }
        else // Start recording
        {
            recorder.newRecording("Recording " + String.valueOf(recordings.size()));
        }
    }
    private void togglePlayer()
    {
        robotHandler.stateHandler.togglePlayer();
        if (!robotHandler.stateHandler.isPlaying) //Stop playing
        {
            player.stop();
        }
        else // Start playing
        {
            player.play(recordings.get(selectedRecording));
        }
    }

    public void recordValues(double leftTrain, double rightTrain)
    {
        if (!recorder.isRecording) return;

        recorder.record(leftTrain, rightTrain);
    }

    public class Recording
    {
        public List<MotorPeriod> recording = new ArrayList<MotorPeriod>();
    }
    public class Recorder
    {
        public boolean isRecording = false;

        private Recording recording;
        private boolean firstCycle = true;

        private double lastLeftValue;
        private double lastRightValue;

        public void record(double leftValue, double rightValue)
        {
            if (firstCycle)
            {
                firstCycle = false;
                lastLeftValue = leftValue;
                lastRightValue = rightValue;
            }
            else if (haveValuesChanged(leftValue, rightValue))
            {
                addMotorPeriod(lastLeftValue, lastRightValue);
            }
            lastLeftValue = leftValue;
            lastRightValue = rightValue;
        }
        private boolean haveValuesChanged(double leftValue, double rightValue)
        {
            return (lastLeftValue != leftValue) || (lastRightValue != rightValue);
        }
        public void start()
        {
            recording = new Recording();
            isRecording = true;
            firstCycle = true;
        }
        public void stop()
        {
            addMotorPeriod(lastLeftValue, lastRightValue);
            isRecording = false;
        }
        public Recording save()
        {
            return recording;
        }
        private void addMotorPeriod(double leftValue, double rightValue)
        {
            recording.recording.add(new MotorPeriod(lastLeftValue, lastRightValue, Timer.getFPGATimestamp()));
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
        
        public void run()
        {
            if (!isPlaying) return;
            MotorPeriod motorPeriod = findSuitableMotorPeriod();
            robotHandler.driveHandler.differentialDrive.tankDrive(motorPeriod.speedLeft, motorPeriod.speedLeft);
        }
        public void start(Recording recording)
        {
            startTime = Timer.getFPGATimestamp();
            this.recording = recording;
            isPlaying = true;
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
