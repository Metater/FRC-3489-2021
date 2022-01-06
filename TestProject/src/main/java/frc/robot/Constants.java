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

    public static final int LeftDriveJoystickPort = 0;
    public static final int RightDriveJoystickPort = 1;
    public static final int ManipulatorJoystickPort = 2;

    public static final double EncoderClicksForTenFeet = 4000;
    public static final double EncoderClicksForFiveFeet = 2000;
    public static final double AutoDriveSpeed = 0.65;

    public static final double EncoderClicksFor180Turn = 371;
    public static final double Auto180TurnSpeed = 0.58;

}
