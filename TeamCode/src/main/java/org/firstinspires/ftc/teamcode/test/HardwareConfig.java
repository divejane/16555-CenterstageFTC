package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*
*
* Robot hardware config file
*
 */

public class HardwareConfig{

    public Servo             launchServo,
                   leftClawWrist,  rightClawWrist,
                   leftClawFinger, rightClawFinger;

    public DcMotor           slideMotor,
                   leftFrontMotor, rightFrontMotor,
                   leftBackMotor,  rightBackMotor,
                   leftHangTower,  rightHangTower;

    public HardwareConfig() {
        
    }
}

// todo: should just have to initialize the class in whatever file with a constructor (put the hw init inside in constructor). Use KB as ref
