package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp (name = "DellaTeleOp")
public class DellaTeleOp extends LinearOpMode {
    private DcMotorEx Shooter;

    private DcMotor Intake;
    private DcMotor Funnel1;
    private DcMotor Funnel2;

    private DcMotor fRight;
    private DcMotor fLeft;
    private DcMotor bRight;
    private DcMotor bLeft;

    private boolean wasUpPressed = false;
    private boolean wasDownPressed = false;

    //private boolean aWasPressed = false;

    private int power = 1150;

    public void runOpMode(){

        Intake = hardwareMap.get(DcMotor.class, "intake");

        Funnel1 = hardwareMap.get(DcMotor.class, "funnel1");
        Funnel2 = hardwareMap.get(DcMotor.class, "funnel2");
        Funnel2.setDirection(DcMotorSimple.Direction.REVERSE);

        Shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        Shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        Shooter.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        fRight= hardwareMap.get(DcMotor.class,"fRight");
        fLeft= hardwareMap.get(DcMotor.class,"fLeft");
        bRight= hardwareMap.get(DcMotor.class,"bRight");
        bLeft= hardwareMap.get(DcMotor.class,"bLeft");

        fRight.setDirection(DcMotor.Direction.REVERSE);
        fLeft.setDirection(DcMotor.Direction.FORWARD);
        bRight.setDirection(DcMotor.Direction.REVERSE);
        bLeft.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();
        while (opModeIsActive()){
//----------------INTAKE--------------------
            if (gamepad2.left_bumper) {
                Intake.setPower(1);
            }
            else{
                Intake.setPower(0);
            }
//----------------SHOOTER---------------------
            if (gamepad2.dpad_up && !wasUpPressed) {
                power += 100; // Increase power by 100
            }
            // Update the tracking variable to remember this frame's state
            wasUpPressed = gamepad2.dpad_up;

            // --- DPAD DOWN LOGIC ---
            if (gamepad2.dpad_down && !wasDownPressed) {
                power -= 100; // Decrease power by 100
            }
            wasDownPressed = gamepad2.dpad_down;

            if (gamepad2.right_bumper) {
                Shooter.setVelocity(power);
            } else {
                Shooter.setVelocity(0);
            }
//----------------FUNNEL WHEELS-------------------
            if (gamepad2.a) {
                Funnel1.setPower(1);
                Funnel2.setPower(1);

            }
            else{
                Funnel1.setPower(0);
                Funnel2.setPower(0);

            }

//----------------DRIVE-------------------
            double drive;
            double strafe;
            double rotation;
            drive = gamepad1.right_stick_y;
            strafe = gamepad1.right_stick_x;
            rotation = gamepad1.left_stick_x;

            fRight.setPower(drive+rotation+strafe);
            fLeft.setPower(drive-rotation-strafe);
            bRight.setPower(drive+rotation-strafe);
            bLeft.setPower(drive-rotation+strafe);

            telemetry.addData("Shooter Power: ", power);
            telemetry.update();

        }
    }
}