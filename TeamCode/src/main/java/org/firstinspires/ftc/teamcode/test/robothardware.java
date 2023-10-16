//
// ROBOT HARDWARE CONFIG FILE
//
package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class robothardware {

    public Servo             launchServo,
                   leftClawWrist,  rightClawWrist,
                   leftClawFinger, rightClawFinger;

    public DcMotor           slideMotor,
                   leftFrontMotor, rightFrontMoto,
                   leftBackMotor,  rightBackMotor,
                   leftHangTower,  rightHangTower;

}

// todo: should just have to initialize the class in whatever file with a constructor (put the hw init inside in constructor). Use KB as ref
