package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class PIDF_Slide extends OpMode {
    private PIDController controller;

    public static double p = 0, i = 0, d = 0;
    public static double f = 0;

    public static int target = 0;

    private DcMotorEx slideMotor;

    @Override
    public void init() {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");
    }

    @Override
    public void loop() {
        int slideHeight = slideMotor.getCurrentPosition();
        controller.setPID(p, i, d);
        double pid = controller.calculate(slideHeight, target);
        double ff  = (f * slideHeight) + pid;

        double power = pid + ff;

        slideMotor.setPower(power);

        telemetry.addData("pos ", slideHeight);
        telemetry.addData("target", target);
        telemetry.update();
    }
}
