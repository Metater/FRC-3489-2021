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
    public static final double MAN_STICK_SPEED_CUTOFF = 0.35;

    public static final double ZUCC_SPEED = -0.9;
    //public static final double ZUCC_SPEED = -0.25;
    public static final double ZUCC_JAM_SPEED = 0.16;

    public static final double ZUCC_JAM_CURRENT = 19;

    public static final double INTAKE_BELT_FRONT_SPEED = 0.6; // Speed of belt going into the robot
    public static final double INTAKE_BELT_REAR_SPEED = -0.21; // Speed of belt going into the robot

    public final class Buttons
    {
        public static final int SCISSOR_LIFT_LEFT = 6;
        public static final int SCISSOR_LIFT_RIGHT = 8;

        public static final int BALL_INTAKE_ROLLER_TOGGLE = 1; // Man Stick

        public static final int LIFT_COLOR_WHEEL = 4;
        public static final int SPIN_COLOR_WHEEL = 12;

        // Drive Stick
        public static final int SWITCH_FRONT = 7;
        public static final int TOGGLE_SPEED_SCALE = 5;
        public static final int TOGGLE_RECORDING = 6;
        public static final int TOGGLE_PLAYBACK = 4;

    }

    public final class USB
    {
        public static final int JOYSTICK_DRIVE_LEFT = 0;
        public static final int JOYSTICK_DRIVE_RIGHT = 1;
        public static final int JOYSTICK_MANIPULATOR = 2;
    }

    public final class Motors
    {
        // Drive Motors
        public static final int DRIVE_LEFT_FRONT = 1; // TalonSRX
        public static final int DRIVE_RIGHT_FRONT = 2; // TalonSRX
        public static final int DRIVE_LEFT_FOLLOWER = 3; // TalonSRX
        public static final int DRIVE_RIGHT_FOLLOWER = 4; // TalonSRX

        // Ball System Motors
        public static final int INTAKE_BELT_FRONT = 5; // TalonSRX
        public static final int INTAKE_BELT_REAR = 10; // TalonFX
        public static final int INTAKE_ROLLER = 8; // TalonSRX

        // Color Sensor System Motors
        public static final int COLOR_WHEEL = 6; // TalonSRX

        // Winch Motor
        public static final int WINCH = 9; // TalonFX
    }

    public final class Solenoids
    {
        // The PCM CAN ID that is used for all pnuematics
        public static final int PCM_NUMBER = 11;

        // Pnuematic Control Module channels, and their uses
        public static final int SCISSOR_LIFT_PART_1 = 0; // Dont know if right or left, prob left
        public static final int SCISSOR_LIFT_PART_2 = 1; // Dont know if right or left, prob right
        public static final int INTAKE_ROLLER = 2;
        public static final int COLOR_WHEEL = 3;
    }

    public final class MotorSpeeds
    {
        public static final double INTAKE_ROLLER_SPEED = -0.9;
        public static final double INTAKE_ROLLER_SPEED_WHEN_STATOR_CURRENT_IS_MORE_THAN_3_5 = 0.5;
    }

    public final class DigitalInputs
    {
        public static final int INTAKE_BELT_BOTTOM_SENSOR = 0;
    }

    public final class Clicks
    {
        public static final double BALL_SYSTEM_CLICKS_PER_INDEX = 20000;
    }

    public final class MotorOffset
    {
        // May want to make a scaling thing, instead, bc friction, and account for negatives
        public static final double RIGHT_DRIVE_TRAIN_FRICTION_CORRECTION = 0;
    }


}
