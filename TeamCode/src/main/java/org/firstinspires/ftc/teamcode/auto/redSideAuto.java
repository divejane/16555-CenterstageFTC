package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class redSideAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        DcMotor leftFrontMotor  = hardwareMap.dcMotor.get("leftFrontMotor");
        DcMotor leftBackMotor   = hardwareMap.dcMotor.get("leftBackMotor");
        DcMotor rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        DcMotor rightBackMotor  = hardwareMap.dcMotor.get("rightBackMotor");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {



        }
    }
    public void encoderSeq (int fL, int bL, int fR, int bR) {

    }
}
