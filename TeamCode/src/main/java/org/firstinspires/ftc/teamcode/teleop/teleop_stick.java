package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class teleop_stick extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        boolean GP2SchemeToggle = false;

        // HARDWARE
        Servo launchServo       = hardwareMap.servo.get("launchServo");
        Servo leftClawWrist     = hardwareMap.servo.get("leftClawWrist");
        Servo rightClawWrist    = hardwareMap.servo.get("rightClawWrist");
        Servo leftClawFinger    = hardwareMap.servo.get("leftClawFinger");
        Servo rightClawFinger   = hardwareMap.servo.get("rightClawFinger");

        rightClawFinger.setDirection(Servo.Direction.REVERSE);

        double droneHold = launchServo.getPosition();
        launchServo.setPosition(droneHold);

        DcMotor leftFrontMotor  = hardwareMap.dcMotor.get("leftFrontMotor");
        DcMotor leftBackMotor   = hardwareMap.dcMotor.get("leftBackMotor");
        DcMotor rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        DcMotor rightBackMotor  = hardwareMap.dcMotor.get("rightBackMotor");
        DcMotorEx slideMotor    = hardwareMap.get(DcMotorEx.class, "slideMotor");
        DcMotor leftHangTower   = hardwareMap.dcMotor.get("leftHangTower");
        DcMotor rightHangTower  = hardwareMap.dcMotor.get("rightHangTower");

        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);

        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        slideMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double GP2Target  =  gamepad2.left_stick_y;
            int slideHeight   =  slideMotor.getCurrentPosition();
            double forward    =  gamepad1.left_stick_y;
            double strafe     =  gamepad1.left_stick_x;
            double turn       =  -gamepad1.right_stick_x;

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
                    launchServo.setDirection(Servo.Direction.REVERSE);
                    launchServo.setPosition(1);
                }


            // GAMEPAD 2
                // Control Scheme Switch
                if (gamepad2.dpad_up) {
                    GP2SchemeToggle = true;
                }
                if (gamepad2.dpad_left) {
                    GP2SchemeToggle = false;
                }

                // Hang Control
                if (GP2SchemeToggle) {
                    leftHangTower.setPower(GP2Target);
                    rightHangTower.setPower(-GP2Target);
                }
                // Claw Control
               if (!GP2SchemeToggle) {
                    // Height limiter
                    if (slideHeight <= 3400 || GP2Target > 0) {

                        // FF
                        double power = -GP2Target + 0.07;
                        slideMotor.setPower(power);

                        // Claw Raise
                        if (slideHeight < 300) {
                            leftClawWrist.setPosition(0.24);
                            rightClawWrist.setPosition(0.26);
                        } else {
                            leftClawWrist.setPosition(0.5);
                            rightClawWrist.setPosition(0);
                        }

                    }
                    if (gamepad2.left_bumper) {
                        leftClawFinger.setPosition(0.48); // 0.47 for vert
                    }
                    if (gamepad2.left_trigger >= .75) {
                        leftClawFinger.setPosition(0.46);
                    }
                    if (gamepad2.right_bumper) {
                        rightClawFinger.setPosition(0.98);
                    }
                    if (gamepad2.right_trigger >= .75) {
                        rightClawFinger.setPosition(0.89);
                    }
                }
        }
    }}
