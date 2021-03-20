package frc.robot.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

// Redbull Replay
public class RecordingAndPlaybackHandler {

    private RobotHandler robotHandler;

    public CycleRecorder recorder;
    public CyclePlayer player;

    public List<CycleRecording> recordings = new ArrayList<CycleRecording>();
    public int selectedRecording = -1;

    public RecordingAndPlaybackHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
        recorder = new CycleRecorder(robotHandler);
        player = new CyclePlayer(robotHandler);
    }

    public void robotInit()
    {
        robotHandler.robot.addPeriodic(() ->
        {
            //System.out.println("Selected Recording: " + robotHandler.recordingAndPlaybackHandler.selectedRecording);
        }, 1, 0);
    }

    public void teleopInit()
    {
        selectedRecording = 0;
    }

    public void autonomousInit()
    {
        selectedRecording = robotHandler.shuffleboardHandler.getSelectedRecording();
        selectedRecording = 0;
    }

    public void autonomousPeriodic()
    {
        if (!player.isPlaying)
            togglePlayer();
        player.run();
    }

    public void disabledInit()
    {
        if (player.isPlaying)
            togglePlayer();
    }

    public void teleopPeriodic()
    {
        if (robotHandler.inputHandler.isEitherOrDriveJoystickButton(Constants.Buttons.TOGGLE_RECORDING))
        {
            if (robotHandler.stateHandler.lastRecordingToggleTime + 1 < Timer.getFPGATimestamp())
                if (!player.isPlaying) toggleRecorder();
        }
        else if (robotHandler.inputHandler.isEitherOrDriveJoystickButton(Constants.Buttons.TOGGLE_PLAYBACK))
        {
            if (robotHandler.stateHandler.lastPlayerToggleTime + 1 < Timer.getFPGATimestamp())
                if (!recorder.isRecording && recordings.size() > 0) togglePlayer();
        }
        if (recorder.isRecording)
        {
            
        }
        else if (player.isPlaying)
        {
            player.run();
        }
    }
    private void toggleRecorder()
    {
        robotHandler.stateHandler.toggleRecording();
        if (!robotHandler.stateHandler.isRecording) // Stopped recorder
        {
            recorder.stop();
            CycleRecording recording = recorder.save();
            recordings.add(recording);
            robotHandler.shuffleboardHandler.printBooleanToWidget("Recording", false);
            robotHandler.shuffleboardHandler.printDoubleToWidget("Recording L", recording.recording.size());
        }
        else // Starting recorder
        {
            recorder.start();
            robotHandler.shuffleboardHandler.printBooleanToWidget("Recording", true);
        }
    }
    private void togglePlayer()
    {
        robotHandler.stateHandler.togglePlayer();
        if (!robotHandler.stateHandler.isPlaying) //Stopping player
        {
            player.stop();
            robotHandler.shuffleboardHandler.printBooleanToWidget("Playing", false);
        }
        else // Starting player
        {
            player.start(recordings.get(selectedRecording));
            robotHandler.shuffleboardHandler.printBooleanToWidget("Playing", true);
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
        private RobotHandler robotHandler;

        public boolean isRecording = false;
        public double startTime;

        private Recording recording;
        private boolean firstCycle = true;

        private double lastLeftValue;
        private double lastRightValue;

        public Recorder(RobotHandler robotHandler)
        {
            this.robotHandler = robotHandler;
        }

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
        public void start()
        {
            recording = new Recording();
            isRecording = true;
            startTime = Timer.getFPGATimestamp();
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
            double timeSinceStart = Timer.getFPGATimestamp() - startTime;
            recording.recording.add(new MotorPeriod(lastLeftValue, lastRightValue, timeSinceStart));
        }
        private boolean haveValuesChanged(double leftValue, double rightValue)
        {
            return (lastLeftValue != leftValue) || (lastRightValue != rightValue);
        }
    }
    public class Player
    {
        private RobotHandler robotHandler;

        public boolean isPlaying = false;

        private double startTime;
        private Recording recording;

        public Player(RobotHandler robotHandler)
        {
            this.robotHandler = robotHandler;
        }
        
        public void run()
        {
            if (!isPlaying) return;
            MotorPeriod motorPeriod = findSuitableMotorPeriod();
            robotHandler.driveHandler.differentialDrive.tankDrive(motorPeriod.speedLeft, motorPeriod.speedRight);
            //System.out.println(motorPeriod.speedLeft + ":::" + motorPeriod.speedRight);
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
            double timeSinceStart = getTimeSinceStart();

            System.out.println(recording.recording.size());

            for (int i = recording.recording.size()-1; i >= 0; i--)
            {
                MotorPeriod motorPeriod = recording.recording.get(i);
                if (motorPeriod.endTime < timeSinceStart)
                    return motorPeriod;
            }

            return new MotorPeriod(0, 0, 0);
        }
        private double getTimeSinceStart()
        {
            return Timer.getFPGATimestamp() - startTime;
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


    public class CycleRecording
    {
        public List<MotorCycle> recording = new ArrayList<MotorCycle>();
    }
    public class CycleRecorder
    {
        private RobotHandler robotHandler;

        private CycleRecording recording;

        public boolean isRecording = false;

        public CycleRecorder(RobotHandler robotHandler)
        {
            this.robotHandler = robotHandler;
        }

        public void record(double leftValue, double rightValue)
        {
            recording.recording.add(new MotorCycle(leftValue, rightValue));
        }
        public void start()
        {
            recording = new CycleRecording();
            isRecording = true;
        }
        public void stop()
        {
            isRecording = false;
        }
        public CycleRecording save()
        {
            return recording;
        }
    }
    public class CyclePlayer
    {
        private RobotHandler robotHandler;

        private CycleRecording recording;

        public boolean isPlaying = false;

        public CyclePlayer(RobotHandler robotHandler)
        {
            this.robotHandler = robotHandler;
        }

        int cycle = 0;
        public void run()
        {
            if (!isPlaying) return;
            if (cycle > recording.recording.size()-1)
            {
                robotHandler.driveHandler.differentialDrive.tankDrive(0, 0);
                return;
            }
            MotorCycle motorCycle = recording.recording.get(cycle);
            robotHandler.driveHandler.differentialDrive.tankDrive(motorCycle.speedLeft, motorCycle.speedRight);
            cycle++;
        }

        public void start(CycleRecording recording)
        {
            this.recording = recording;
            isPlaying = true;
            cycle = 0;
        }
        public void stop()
        {
            isPlaying = false;
        }
    }
    public class MotorCycle
    {
        public double speedLeft;
        public double speedRight;
        public MotorCycle(double speedLeft, double speedRight)
        {
            this.speedLeft = speedLeft;
            this.speedRight = speedRight;
        }
    }
}
