package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class redSideAuto extends LinearOpMode {

    public DcMotor leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            chassisMove(0,0,0,0);

        }
    }
    public void chassisMove (int fL, int fR, int bL, int bR) {

        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontMotor.setTargetPosition(fL);
        leftBackMotor.setTargetPosition(bL);
        rightFrontMotor.setTargetPosition(fR);
        rightBackMotor.setTargetPosition(bR);

        leftFrontMotor.setPower(0.5);
        leftBackMotor.setPower(0.5);
        rightFrontMotor.setPower(0.5);
        rightBackMotor.setPower(0.5);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
        while (opModeIsActive() && (BackRight.isBusy() || BackLeft.isBusy() || FrontRight.isBusy() || FrontLeft.isBusy())) {
        }

        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        }
}

