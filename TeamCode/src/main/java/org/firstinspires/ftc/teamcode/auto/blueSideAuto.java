package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class blueSideAuto extends LinearOpMode {

    public DcMotor leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");

        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            /*
            just change the number in chassisMove. dont mess with anything else
             */

            chassisMove(1500);

        }
    }
    public void chassisMove (int strafe) {

        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontMotor.setTargetPosition(-strafe);
        leftBackMotor.setTargetPosition(strafe);
        rightFrontMotor.setTargetPosition(strafe);
        rightBackMotor.setTargetPosition(-strafe);

        leftFrontMotor.setPower(0.5);
        leftBackMotor.setPower(0.5);
        rightFrontMotor.setPower(0.5);
        rightBackMotor.setPower(0.5);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (opModeIsActive() && (rightBackMotor.isBusy() || leftBackMotor.isBusy() || rightFrontMotor.isBusy() || leftFrontMotor.isBusy())) {
        }

        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
        }
    }

