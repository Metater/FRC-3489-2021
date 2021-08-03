// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{
    public static final double DriveJoystickThreshold = 0.15;
    public static final double ManipulatorJoystickThreshold = 0.15;

    public static final double BallSystemCPI = 20000;

    public final class Button
    {
        // Manipulator
        public static final int ToggleIntake = 1;
        public static final int Climb1 = 6;
        public static final int Climb2 = 8;
        public static final int LiftCPSpinner = 4;
        public static final int SpinCPSpinner = 12;
        public static final int ResetCPStage1 = 11;
        public static final int ResetCPStage2 = 10;

        // Drive
        public static final int SwitchFront = 7;
    }

    public final class MotorSpeed
    {
        public static final double IntakeRoller = -0.9;
        public static final double IntakeBeltFront = -0.9;
        public static final double IntakeBeltRear = -0.65;
    }
}
