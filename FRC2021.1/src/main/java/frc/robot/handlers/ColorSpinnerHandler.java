package frc.robot.handlers;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;

import com.revrobotics.ColorMatchResult;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;

public class ColorSpinnerHandler {

  public enum ControlPanelStage
  {
    Stage1,
    Stage2,
    Stage3
  }

  private ControlPanelStage stage = ControlPanelStage.Stage1;

    public Solenoid colorPnuematics = new Solenoid(11, 3);
    public WPI_TalonSRX colorSpinner = new WPI_TalonSRX(6);

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch colorMatcher = new ColorMatch();
    private final Color blueTarget = ColorMatch.makeColor(0.23, 0.46, 0.31);
    private final Color greenTarget = ColorMatch.makeColor(0.27, 0.48, 0.25);
    //private final Color greenTarget = ColorMatch.makeColor(0.21, 0.53, 0.25);
    private final Color redTarget = ColorMatch.makeColor(0.33, 0.45, 0.22);
    private final Color yellowTarget = ColorMatch.makeColor(0.30, 0.51, 0.19);

    private double timeOf3Spins = -1;

    private String colorToFind = "?";
    private String previousColor = "?";
    private boolean keepSpinning = true;

    private int redYellowPasses = 0;
    
    private RobotHandler robotHandler;

    public ColorSpinnerHandler(RobotHandler robotHandler)
    {
        this.robotHandler = robotHandler;
    }

    public void robotInit()
    {
        colorMatcher.addColorMatch(blueTarget);
        colorMatcher.addColorMatch(yellowTarget);
        colorMatcher.addColorMatch(greenTarget);
        colorMatcher.addColorMatch(redTarget);  
    }

    public void teleopPeriodic()
    {
        trySpinAndLiftColor();

        Color detectedColor = colorSensor.getColor();
        String colorString;
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        if (match.color == blueTarget) {
          colorString = "Blue";
          colorToFind = "B";
        } else if (match.color == redTarget) {
          colorString = "Red";
          colorToFind = "R";
        } else if (match.color == greenTarget) {
          colorString = "Green";
          colorToFind = "G";
        } else if (match.color == yellowTarget) {
          colorString = "Yellow";
          colorToFind = "Y";
        } else {
          colorString = "Unknown";
          colorToFind = "?";
        }

        tryStage1();

        if (Timer.getFPGATimestamp() - timeOf3Spins > 5 && (redYellowPasses >= 6))
        {
          if (stage == ControlPanelStage.Stage1) stage = ControlPanelStage.Stage2;
        }



        previousColor = colorToFind;

        robotHandler.shuffleboardHandler.printDoubleToWidget("Red", detectedColor.red);
        robotHandler.shuffleboardHandler.printDoubleToWidget("Green", detectedColor.green);
        robotHandler.shuffleboardHandler.printDoubleToWidget("Blue", detectedColor.blue);
        robotHandler.shuffleboardHandler.printDoubleToWidget("Confidence", match.confidence);
        robotHandler.shuffleboardHandler.printStringToWidget("Detected Color", colorString);
        robotHandler.shuffleboardHandler.printDoubleToWidget("Red-yellow passes", redYellowPasses);
        robotHandler.shuffleboardHandler.printDoubleToWidget("Spinner current", colorSpinner.getStatorCurrent());
        robotHandler.shuffleboardHandler.printStringToWidget("Stage", stage.toString());
        robotHandler.shuffleboardHandler.printStringToWidget("Game Data", DriverStation.getInstance().getGameSpecificMessage());

        if (robotHandler.inputHandler.joystickManipulator.getRawButton(Constants.Buttons.RESET_COLOR_SPINS))
        {
          keepSpinning = true;
          redYellowPasses = 0;
          stage = ControlPanelStage.Stage1;
        }
        if (robotHandler.inputHandler.joystickManipulator.getRawButton(Constants.Buttons.RESET_COLOR_SPINS-1))
        {
          keepSpinning = true;
          redYellowPasses = 0;
          stage = ControlPanelStage.Stage2;
        }
    }

    private void tryStage1()
    {
      if (stage == ControlPanelStage.Stage1)
      {
        if (colorToFind == "Y" && previousColor == "R")
        {
          redYellowPasses++;
          if (redYellowPasses > 4)
          {
            keepSpinning = false;
            timeOf3Spins = Timer.getFPGATimestamp();
          }
        }
      }
    }

    private void trySpinAndLiftColor()
    {
        if (robotHandler.inputHandler.shouldLiftColorSpinner())
            colorPnuematics.set(true);
        else
            colorPnuematics.set(false);
        if (robotHandler.inputHandler.shouldSpinColorSpinner())
        {
          if (stage == ControlPanelStage.Stage1)
          {
            if (keepSpinning) colorSpinner.set(0.6);
            else colorSpinner.set(-1);
          }
          else if (stage == ControlPanelStage.Stage2)
          {
            char gameData = DriverStation.getInstance().getGameSpecificMessage().charAt(0);

            robotHandler.shuffleboardHandler.printStringToWidget("Color to find", colorToFind);
            String shiftedColor = getShiftedColor(gameData);
            robotHandler.shuffleboardHandler.printStringToWidget("Shifted color", shiftedColor);
            if (colorToFind != shiftedColor)
            {
              colorSpinner.set(0.2);
            }
            else
            {
              colorSpinner.stopMotor();
              stage = ControlPanelStage.Stage3;
            }
          }
          else
          {
            colorSpinner.stopMotor();
          }
        }
        else
            colorSpinner.stopMotor();
    }

    private String getShiftedColor(char wantedColor)
    {
      switch (wantedColor)
      {
        case 'R':
          return "G";
        case 'Y':
          return "B";
        case 'G':
          return "R";
        case 'B':
          return "Y";
      }
      return null;
    }
}
