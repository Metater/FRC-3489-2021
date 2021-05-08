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
public final class Constants {

    public final class Thresholds
    {
        public static final double DriveJoystickSpeedCutoff = 0.1;
        public static final double ManipulatorJoystickBeltSystemCutoff = 0.35;
    }

    public final class Speeds
    {
        public static final double IntakeRollerSpeed = -0.9;
        public static final double BeltFrontSpeed = 0.6;
        public static final double BeltRearSpeed = -0.21;
    }

    public final class Devices
    {

    }

    public final class JoystickDriveLeftButtons
    {
        public static final int SwitchFront = 7;
    }
    public final class JoystickDriveRightButtons
    {
        public static final int SwitchFront = 7;
    }
    public final class JoystickManipulatorButtons
    {
        
    }
}
