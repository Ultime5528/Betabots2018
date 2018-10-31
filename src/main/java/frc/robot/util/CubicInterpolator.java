package frc.robot.util;

public class CubicInterpolator {

	private double a, b, c;

	public CubicInterpolator(double a, double b, double c) {

		this.a = a;
		this.b = b;
		this.c = c;

	}

	public double interpolate(double x) {

		if (x >= c) {
			
			return b + (1 - b) * (a * x * x * x + (1 - a) * x);
		
		} else if (x <= -c) {
		
			return -b + (1 - b) * (a * x * x * x + (1 - a) * x);
		
		} else {
			
			return a = interpolate(c) / c * x;
		
		}
	}

	public double getA() {
		return a;
	}

    public void stA(double a) {

		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

}