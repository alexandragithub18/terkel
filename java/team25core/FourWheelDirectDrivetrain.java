package team25core;

/*
 * FTC Team 25: cmacfarl, January 23, 2017
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

public class FourWheelDirectDrivetrain implements Drivetrain {

    DcMotor rearLeft;
    DcMotor rearRight;
    DcMotor frontLeft;
    DcMotor frontRight;

    int encoderTicksPerInch;
    double multiplier;

    public FourWheelDirectDrivetrain(int encoderTicksPerInch, DcMotor frontRight, DcMotor rearRight, DcMotor frontLeft, DcMotor rearLeft)
    {
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;

        this.encoderTicksPerInch = encoderTicksPerInch;
        this.multiplier = 1.0;

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        rearRight.setDirection(DcMotor.Direction.REVERSE);
    }

    public FourWheelDirectDrivetrain(int encoderTicksPerInch, double pivotMultiplier, DcMotor frontRight, DcMotor rearRight, DcMotor frontLeft, DcMotor rearLeft)
    {
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;

        this.encoderTicksPerInch = encoderTicksPerInch;
        this.multiplier = pivotMultiplier;

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        rearRight.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void resetEncoders()
    {
        rearLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rearRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }

    @Override
    public void encodersOn()
    {
        rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
    }

    @Override
    public void straight(double speed)
    {
        frontRight.setPower(speed);
        frontLeft.setPower(speed);
        rearRight.setPower(speed);
        rearLeft.setPower(speed);
    }

    @Override
    public void turn(double speed)
    {
        frontRight.setPower(speed);
        rearRight.setPower(speed);
        frontLeft.setPower(-speed);
        rearLeft.setPower(-speed);
    }

    @Override
    public void pivotTurn(PivotSide side, double speed)
    {
        if (side == PivotSide.RIGHT) {
            frontLeft.setPower(speed);
            rearLeft.setPower(speed);
            frontRight.setPower(-(1/multiplier) * speed);
            rearRight.setPower(-(1/multiplier) * speed);
        } else if (side == PivotSide.LEFT) {
            frontLeft.setPower(-(1/multiplier) * speed);
            rearLeft.setPower(-(1/multiplier) * speed);
            frontRight.setPower(speed);
            rearRight.setPower(speed);
        }
    }

    @Override
    public void stop()
    {
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        rearLeft.setPower(0.0);
        rearRight.setPower(0.0);
    }
}