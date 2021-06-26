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
    public static final boolean SafetiesEnabled = false;
    public static final double DriveJoystickThreshold = 0.15;
    public static final double ManipulatorJoystickThreshold = 0.15;

    public final class Turret
    {
        public static final double CellavatorWaitSpeed = 0.5;
        public static final double CellavatorLogSpeed = 0.5;
        public static final double BallEntryCurrent = 15;
        public static final double CellavatorLoggingPeriod = 0.3;
        public static final double TurretAcceleratePeriod = 0.5;

        public static final double GoodBallMinCurrentAvg = 25;
        public static final double MediumBallMinCurrentAvg = 20;
        public static final double BadBallMinCurrentAvg = 15;
    }
}
