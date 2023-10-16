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
    double kg = 0;

    public static int target = 0;

    private final double ticks_in_degree = 537.7 / 360;

    private DcMotorEx slideMotor;

    @Override
    public void init() {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");
    }

    @Override
    public void loop() {
        controller.setPID(p, i, d);
        int slideHeight = slideMotor.getCurrentPosition();
        double pid = controller.calculate(slideHeight, target);


    }
}
