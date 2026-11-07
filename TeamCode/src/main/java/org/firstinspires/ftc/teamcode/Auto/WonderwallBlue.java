package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous (name = "WonderwallBlue")
public class WonderwallBlue extends LinearOpMode{

    private DcMotorEx shooter;
    private DcMotor intake, Funnel1, Funnel2;
    private DcMotor fLeft, bLeft, fRight, bRight;

    //private Servo Lever;

    private ElapsedTime runtime = new ElapsedTime();

    //boolean hiveDropped;
    static final double COUNTS_PER_MOTOR_REV = 537.6;
    static final double DRIVE_GEAR_REDUCTION = 1.0;
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double COUNTS_PER_INCH =
            (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                    (WHEEL_DIAMETER_INCHES * 3.1415);

    @Override
    public void runOpMode(){

        fLeft  = hardwareMap.get(DcMotor.class, "fLeft");
        bLeft  = hardwareMap.get(DcMotor.class, "bLeft");
        fRight = hardwareMap.get(DcMotor.class, "fRight");
        bRight = hardwareMap.get(DcMotor.class, "bRight");

        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        intake = hardwareMap.get(DcMotor.class, "intake");
        Funnel1 = hardwareMap.get(DcMotor.class, "funnel1");
        Funnel2 = hardwareMap.get(DcMotor.class, "funnel2");

        //Lever = hardwareMap.get(Servo.class, "Lever"); *Was on Dennis, modifying code for Della

        Funnel2.setDirection(DcMotor.Direction.REVERSE);

        //Lever.setDirection(Servo.Direction.REVERSE);

        fLeft.setDirection(DcMotor.Direction.FORWARD);
        bLeft.setDirection(DcMotor.Direction.FORWARD);
        fRight.setDirection(DcMotor.Direction.REVERSE);
        bRight.setDirection(DcMotor.Direction.REVERSE);

        fLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shooter.setDirection(DcMotorEx.Direction.REVERSE);
        shooter.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        encoderDrive(1, 12, 2);
        encoderStrafe(1, 6, 2, false);
        encoderDrive(-1, 6, 2);

    }
    public void encoderDrive(double speed, double inches, double timeoutS) {
        int moveCounts = (int) (inches * COUNTS_PER_INCH);

        fLeft.setTargetPosition(fLeft.getCurrentPosition() + moveCounts);
        fRight.setTargetPosition(fRight.getCurrentPosition() + moveCounts);
        bLeft.setTargetPosition(bLeft.getCurrentPosition() + moveCounts);
        bRight.setTargetPosition(bRight.getCurrentPosition() + moveCounts);

        fLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fLeft.setPower(Math.abs(speed));
        fRight.setPower(Math.abs(speed));
        bLeft.setPower(Math.abs(speed));
        bRight.setPower(Math.abs(speed));

        while (opModeIsActive() && fLeft.isBusy() && fRight.isBusy() && bLeft.isBusy() && bRight.isBusy()) {
            telemetry.addData("Driving", "Running");
            telemetry.update();
        }

        stopMotors();
        resetEncoders();
    }

    public void encoderDriveTurn(double speed, double inches, double timeoutS, boolean right) {
        int moveCounts = (int) (inches * COUNTS_PER_INCH);

        if (right) {
            fLeft.setTargetPosition(-moveCounts);
            bLeft.setTargetPosition(-moveCounts);
            fRight.setTargetPosition(moveCounts);
            bRight.setTargetPosition(moveCounts);
        } else {
            fLeft.setTargetPosition(moveCounts);
            bLeft.setTargetPosition(moveCounts);
            fRight.setTargetPosition(-moveCounts);
            bRight.setTargetPosition(-moveCounts);
        }

        fLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fLeft.setPower(Math.abs(speed));
        fRight.setPower(Math.abs(speed));
        bLeft.setPower(Math.abs(speed));
        bRight.setPower(Math.abs(speed));

        while (opModeIsActive() &&
                fLeft.isBusy() && fRight.isBusy() &&
                bLeft.isBusy() && bRight.isBusy()) {
            telemetry.addData("Turning", "Running");
            telemetry.update();
        }

        stopMotors();
        resetEncoders();
    }

    public void encoderStrafe(double speed, double inches, double timeoutS, boolean right) {
        int moveCounts = (int) (inches * COUNTS_PER_INCH);

        if (right) {
            fLeft.setTargetPosition(moveCounts);
            bLeft.setTargetPosition(-moveCounts);
            fRight.setTargetPosition(-moveCounts);
            bRight.setTargetPosition(moveCounts);
        } else {
            fLeft.setTargetPosition(-moveCounts);
            bLeft.setTargetPosition(moveCounts);
            fRight.setTargetPosition(moveCounts);
            bRight.setTargetPosition(-moveCounts);
        }

        fLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fLeft.setPower(Math.abs(speed));
        fRight.setPower(Math.abs(speed));
        bLeft.setPower(Math.abs(speed));
        bRight.setPower(Math.abs(speed));

        while (opModeIsActive() &&
                fLeft.isBusy() && fRight.isBusy() &&
                bLeft.isBusy() && bRight.isBusy()) {
            telemetry.addData("Turning", "Running");
            telemetry.update();
        }

        stopMotors();
        resetEncoders();
    }

    private void stopMotors() {
        fLeft.setPower(0);
        fRight.setPower(0);
        bLeft.setPower(0);
        bRight.setPower(0);
    }

    private void resetEncoders() {
        fLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
