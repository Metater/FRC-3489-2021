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

    public static final int OFF = 0;
    public static final int ON = 1;

    public static final double DRIVE_SPEED_CUTOFF = 0.1;

    public final class Buttons {

    public static final int SCISSOR_LIFT_RIGHT = 8;
    public static final int SCISSOR_LIFT_LEFT = 6;

    public static final int BALL_INTAKE = 1;

    public static final int LIFT_COLOR_WHEEL = 4;
    public static final int SPIN_COLOR_WHEEL = 12;

    public static final int SWITCH_FRONT = 7;

    public static final int BLEH = 5;
    }

    public final class USB {
        public static final int JOYSTICK_DRIVE_LEFT = 1;
        public static final int JOYSTICK_DRIVE_RIGHT = 2;
        public static final int JOYSTICK_MANIPULATOR = 3;
    }


}
