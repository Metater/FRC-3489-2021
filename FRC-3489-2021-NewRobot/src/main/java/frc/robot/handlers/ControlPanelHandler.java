package frc.robot.handlers;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.shared.interfaces.IButtonListener;
import frc.robot.shared.interfaces.IRobotListener;
import frc.robot.shared.interfaces.ITeleopListener;
import frc.robot.shared.types.input.ButtonLocation;
import frc.robot.shared.types.input.ButtonUpdateEventType;
import frc.robot.shared.types.input.JoystickType;
import frc.robot.shared.types.input.buttonUpdate.BaseButtonUpdate;
import frc.robot.shared.types.input.buttonUpdate.RawButtonUpdate;
import frc.robot.shared.types.robot.PeriodicType;
import frc.robot.shared.types.ControlPanelStageType;

public class ControlPanelHandler extends BaseHandler implements IRobotListener, ITeleopListener, IButtonListener {

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch colorMatcher = new ColorMatch();
    private final Color blueTarget = ColorMatch.makeColor(0.23, 0.46, 0.31);
    private final Color greenTarget = ColorMatch.makeColor(0.27, 0.48, 0.25);
    private final Color redTarget = ColorMatch.makeColor(0.33, 0.45, 0.22);
    private final Color yellowTarget = ColorMatch.makeColor(0.30, 0.51, 0.19);

    private ControlPanelStageType stage = ControlPanelStageType.Stage1;

    private double stage1CompleteTime = 0;

    private char colorToFind = '?';
    private char previousColor = '?';
    private boolean keepSpinning = true;

    private int redYellowPasses = 0;

    public ControlPanelHandler(RobotHandler robotHandler)
    {
        addReferences(robotHandler);
        RawButtonUpdate spinCPSpinner = new RawButtonUpdate(this, PeriodicType.Teleop, "SpinCPSpinner", new ButtonLocation(Constants.Button.SpinCPSpinner, JoystickType.Manipulator));
        RawButtonUpdate resetCPStage1 = new RawButtonUpdate(this, PeriodicType.Teleop, "ResetCPStage1", new ButtonLocation(Constants.Button.ResetCPStage1, JoystickType.Manipulator));
        RawButtonUpdate resetCPStage2 = new RawButtonUpdate(this, PeriodicType.Teleop, "ResetCPStage2", new ButtonLocation(Constants.Button.ResetCPStage2, JoystickType.Manipulator));
        buttonUpdateListenerHandler.addButtonUpdate(spinCPSpinner);
        buttonUpdateListenerHandler.addButtonUpdate(resetCPStage1);
        buttonUpdateListenerHandler.addButtonUpdate(resetCPStage2);
    }

    public void robotInit()
    {
        colorMatcher.addColorMatch(blueTarget);
        colorMatcher.addColorMatch(yellowTarget);
        colorMatcher.addColorMatch(greenTarget);
        colorMatcher.addColorMatch(redTarget);
    }

    public void robotPeriodic()
    {

    }

    public void teleopInit()
    {
        resetStage(ControlPanelStageType.Stage1);
    }

    public void teleopPeriodic()
    {
        Color detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        if (match.color == blueTarget) colorToFind = 'B';
        else if (match.color == redTarget) colorToFind = 'R';
        else if (match.color == greenTarget) colorToFind = 'G';
        else if (match.color == yellowTarget) colorToFind = 'Y';
        else colorToFind = '?';
        stage1();
        if (Timer.getFPGATimestamp() - stage1CompleteTime > 5 && (redYellowPasses >= 6))
        {
            if (stage == ControlPanelStageType.Stage1) stage = ControlPanelStageType.Stage2;
        }
        previousColor = colorToFind;

        shuffleboardHandler.displayString(shuffleboardHandler.tab, "Game Data", DriverStation.getInstance().getGameSpecificMessage());
        shuffleboardHandler.displayString(shuffleboardHandler.tab, "Stage", stage.toString());
    }

    private void stage1()
    {
        if (stage == ControlPanelStageType.Stage1)
        {
            if (colorToFind == 'Y' && previousColor == 'R')
            {
                redYellowPasses++;
                if (redYellowPasses > 4)
                {
                    keepSpinning = false;
                    stage1CompleteTime = Timer.getFPGATimestamp();
                }
            }
        }
    }

    public void update(BaseButtonUpdate buttonUpdate)
    {
        if (buttonUpdate.buttonUpdateName == "SpinCPSpinner")
        {
            if (buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.On)
            {
                deviceContainer.controlPanelPnuematics.set(true);
                if (stage == ControlPanelStageType.Stage1)
                {
                    if (keepSpinning) deviceContainer.controlPanelSpinner.set(0.6);
                    else deviceContainer.controlPanelSpinner.set(-1);
                }
                else if (stage == ControlPanelStageType.Stage2)
                {
                    char gameData = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
                    shuffleboardHandler.displayString(shuffleboardHandler.tab, "Color To Find", String.valueOf(colorToFind));
                    char shiftedColor = getShiftedColor(gameData);
                    shuffleboardHandler.displayString(shuffleboardHandler.tab, "Shifted Color", String.valueOf(shiftedColor));
                    if (colorToFind != shiftedColor)
                    {
                        deviceContainer.controlPanelSpinner.set(0.2);
                    }
                    else
                    {
                        deviceContainer.controlPanelSpinner.stopMotor();
                        stage = ControlPanelStageType.Stage3;
                    }
                }
                else
                {
                    deviceContainer.controlPanelSpinner.stopMotor();
                }
            }
            else if (buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.Off)
            {
                deviceContainer.controlPanelPnuematics.set(false);
                deviceContainer.controlPanelSpinner.stopMotor();
            }
        }
        else if (buttonUpdate.buttonUpdateName == "ResetCPStage1" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.On)
        {
            resetStage(ControlPanelStageType.Stage1);
        }
        else if (buttonUpdate.buttonUpdateName == "ResetCPStage2" && buttonUpdate.buttonUpdateEventType == ButtonUpdateEventType.On)
        {
            resetStage(ControlPanelStageType.Stage2);
        }
    }

    private char getShiftedColor(char wantedColor)
    {
        switch (wantedColor)
        {
            case 'R':
                return 'G';
            case 'Y':
                return 'B';
            case 'G':
                return 'R';
            case 'B':
                return 'Y';
            default:
                return '?';
        }
    }

    private void resetStage(ControlPanelStageType stage)
    {
        keepSpinning = true;
        redYellowPasses = 0;
        this.stage = stage;
    }


}
