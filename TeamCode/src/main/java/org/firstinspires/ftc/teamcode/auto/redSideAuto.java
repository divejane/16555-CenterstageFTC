package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.auto.pipelines.rPropPL;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


@Autonomous
public class redSideAuto extends LinearOpMode {
    OpenCvWebcam webcam;
    rPropPL rPropPL;

    private DcMotor leftBackMotor, leftFrontMotor, rightBackMotor, rightFrontMotor;

    org.firstinspires.ftc.teamcode.auto.pipelines.rPropPL.PropPosition position = null;

    @Override
    public void runOpMode() {
        /**
         * NOTE: Many comments have been omitted from this sample for the
         * sake of conciseness. If you're just starting out with EasyOpenCv,
         * you should take a look at {@link InternalCamera1Example} or its
         * webcam counterpart, {@link WebcamExample} first.
         */

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        rPropPL = new rPropPL();
        webcam.setPipeline(rPropPL);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        leftBackMotor  = hardwareMap.get(DcMotor.class, "leftBackMotor");
        leftFrontMotor  = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        rightBackMotor  = hardwareMap.get(DcMotor.class, "rightBackMotor");
        rightFrontMotor  = hardwareMap.get(DcMotor.class, "rightFrontMotor");

        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);

        DcMotor slideLift       = hardwareMap.dcMotor.get("slideLift");

        while (!isStarted() && !isStopRequested()) {
            position = rPropPL.returnPos();
            telemetry.addData("PROP FOUND @", position );
            telemetry.update();
            sleep(50);
        }

        waitForStart();

        while (opModeIsActive()) {

            webcam.stopStreaming();

            slideLift.setTargetPosition(-500);
            slideLift.setPower(0.75);
            slideLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            sleep(1000);

            telemetry.addData("Auto running using", position );
            telemetry.update();

            if (position.toString().equals("LEFT")) {
                drive(750,750,750,750,0.35);
                sleep(1000);
                drive(1350,0,1350,0,0.35);
                sleep(1000);
                drive(-1350,0,-1350,0,0.35);
                sleep(50000);
            }
            if (position.toString().equals("CENTER")) {
                drive(1400,1400,1400,1400,0.35);
                sleep(1000);
                drive(-500,-500,-500,-500,0.35);
                sleep(50000);
            }
            else {
                drive(750,750,750,750,0.35);
                sleep(1000);
                drive(0,1350,0,1350,0.35);
                sleep(1000);
                drive(0,-1350,0,-1350,0.35);
                sleep(50000);
            }

        }
    }
    public void drive(int rB, int lB, int rF, int lF, double power) {

        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightBackMotor.setTargetPosition(rB);
        leftBackMotor.setTargetPosition(lB);
        rightFrontMotor.setTargetPosition(rF);
        leftFrontMotor.setTargetPosition(lF);

        rightBackMotor.setPower(power);
        leftBackMotor.setPower(power);
        rightFrontMotor.setPower(power);
        leftFrontMotor.setPower(power);

        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Blocking While Loop: doesn't break until all 4 motors have stopped moving
        // Sets power of motors to 0 after the loop breaks
        while (opModeIsActive() && (rightBackMotor.isBusy() || leftBackMotor.isBusy() || rightFrontMotor.isBusy() || leftFrontMotor.isBusy())) {
        }

        rightBackMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftFrontMotor.setPower(0);
    }
}
