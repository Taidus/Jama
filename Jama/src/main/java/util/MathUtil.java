package util;

public class MathUtil {
	public static final double epsilon = 0.0001;
	
	public static boolean doubleEquals(double a, double b){
		return Math.abs(a - b) < epsilon;
	}
}
