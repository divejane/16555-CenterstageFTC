package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        int GP2SchemeToggle = 1; // Gamepad 2's control scheme toggle to switch between claw and hang control layout
        int clawWristToggle = 1;

        // change
        Servo launchServo     = hardwareMap.servo.get("");
        Servo leftClawWrist   = hardwareMap.servo.get("");
        Servo rightClawWrist  = hardwareMap.servo.get("");
        Servo leftClawFinger  = hardwareMap.servo.get("");
        Servo rightClawFinger = hardwareMap.servo.get("");

        DcMotor leftFrontMotor  = hardwareMap.dcMotor.get("");
        DcMotor leftBackMotor   = hardwareMap.dcMotor.get("");
        DcMotor rightFrontMotor = hardwareMap.dcMotor.get("");
        DcMotor rightBackMotor  = hardwareMap.dcMotor.get("");
        DcMotor slideMotor      = hardwareMap.dcMotor.get("");
        DcMotor leftHangTower   = hardwareMap.dcMotor.get("");
        DcMotor rightHangTower  = hardwareMap.dcMotor.get("");

        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double forward =  -gamepad1.left_stick_y;
            double strafe  =  gamepad1.left_stick_x;
            double turn    =  gamepad1.right_stick_x;

            //  Maximum possible power sent to a motor is -1 or 1, but we can sometimes get values from
            //  (forward + strafe + turn) that exceed -1 or 1, so we have to normalize all motors' power
            //  to keep their speed proportional to each other in the off-chance that this does happen
            double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(turn), 1);

            double leftFrontPower  = forward + strafe + turn / denominator;
            double leftBackPower   = forward - strafe + turn / denominator;
            double rightFrontPower = forward - strafe - turn / denominator;
            double rightBackPower  = forward + strafe - turn / denominator;

            leftFrontMotor.setPower(leftFrontPower);
            leftBackMotor.setPower(leftBackPower);
            rightFrontMotor.setPower(rightFrontPower);
            rightBackMotor.setPower(rightBackPower);

            // GAMEPAD 1
                if (gamepad1.dpad_up) {
                    launchServo.setPosition(0); // change
                }

            // GAMEPAD 2
                // Toggle control scheme
                if (gamepad2.dpad_up) {
                    GP2SchemeToggle += 1;
                }

                // Hang Control
                if (GP2SchemeToggle % 2 == 0) {

                }
                // Claw Control
                else {
                    double target = gamepad2.left_stick_y;
                    slideMotor.setPower(target);

                    if (gamepad2.x) {
                        clawWristToggle += 1;
                    }
                    
                }

        }
    }}