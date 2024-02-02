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
        // HARDWARE
        boolean GP2SchemeToggle = false;

        Servo launchServo       = hardwareMap.servo.get("launchServo");

        int slidePos            = 0;

        DcMotor LHangTower      = hardwareMap.dcMotor.get("LHangTower");
        DcMotor RHangTower      = hardwareMap.dcMotor.get("RHangTower");

        DcMotor leftFrontMotor  = hardwareMap.dcMotor.get("leftFrontMotor");
        DcMotor leftBackMotor   = hardwareMap.dcMotor.get("leftBackMotor");
        DcMotor rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        DcMotor rightBackMotor  = hardwareMap.dcMotor.get("rightBackMotor");

        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);

        DcMotor slideLift       = hardwareMap.dcMotor.get("slideLift");
        DcMotor slideExtend     = hardwareMap.dcMotor.get("slideExtend");

        slideLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Servo clawServo         = hardwareMap.servo.get("clawServo");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double forward    =  gamepad1.left_stick_y;
            double strafe     =  -gamepad1.left_stick_x;
            double turn       =  (-gamepad1.right_stick_x)*0.9;

            //  Maximum possible power sent to a motor is -1 or 1, but we can sometimes get values from
            //  (forward + strafe + turn) that exceed -1 or 1, so we have to normalize all motors' power
            //  to keep their speed proportional to each other in the off-chance that this does happen
            double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(turn), 1);

            double leftFrontPower  = -forward + strafe - turn / denominator;
            double leftBackPower   = forward + strafe + turn / denominator;
            double rightFrontPower = forward + strafe - turn / denominator;
            double rightBackPower  = -forward +strafe + turn / denominator;

            leftFrontMotor.setPower(leftFrontPower);
            leftBackMotor.setPower(leftBackPower);
            rightFrontMotor.setPower(rightFrontPower);
            rightBackMotor.setPower(rightBackPower);

            if (gamepad1.dpad_up) {
                launchServo.setPosition(1);
            }

            // GAMEPAD 2

            // Control Scheme Switch
            if (gamepad2.left_bumper) {
                GP2SchemeToggle = true;
            }
            if (gamepad2.right_bumper) {
                GP2SchemeToggle = false;
            }

            if (!GP2SchemeToggle) {

                if (gamepad2.a) {
                    clawServo.setPosition(0);
                }

                if (gamepad2.b) {
                    clawServo.setPosition(0.05);
                }

                if (gamepad2.dpad_up) { slidePos = 2; }
                if (gamepad2.dpad_left) { slidePos = 1; }
                if (gamepad2.dpad_down) { slidePos = 0; }

                if (slidePos == 2) {
                    slideLift.setTargetPosition(-1000);
                    slideLift.setPower(0.75);
                    slideLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slideExtend.setPower(gamepad2.left_stick_y);
                }

                if (slidePos == 1) {
                    slideLift.setTargetPosition(-500);
                    slideLift.setPower(0.75);
                    slideLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slideExtend.setPower(0.1);
                }

                if (slidePos == 0) {
                    slideLift.setTargetPosition(0);
                    slideLift.setPower(0.75);
                    slideLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slideExtend.setPower(0.3);
                }

            }

            if (GP2SchemeToggle) {
                LHangTower.setPower(-gamepad2.left_stick_y);
                RHangTower.setPower(gamepad2.left_stick_y);
            }

                }
            }
        }

