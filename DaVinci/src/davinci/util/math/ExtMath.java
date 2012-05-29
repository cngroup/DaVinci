/*
 * DavinCi is a light weighted visualization framework and toolkit. 
 * The design of DavinCi based on the information visualization reference model 
 * and design patterns described in "Heer, J. & Agrawala, M., 2006. 
 * Software Design Patterns for Information Visualization. 
 * IEEE TRANSACTIONS ON VISUALIZATION AND COMPUTER GRAPHICS, 12(5), p.853."
 * 
 * The original motivation of creating this project is to design a light weighted, 
 * simple and easy to use information visualization framework that facilitates the 
 * Ph.D study of the author.
 * 
 * DavinCi is under the MIT opensource license.
 * 
 * Author : Nan Cao(nancao@cse.ust.hk)
 * Date : 1st June 2010
 */
package davinci.util.math;

public class ExtMath {

	public static double clamp(double low, double value, double high) {
		if (value < low)
			return low;
		if (value > high)
			return high;
		return value;
	}

	public static float clamp(float low, float value, float high) {
		if (value < low)
			return low;
		if (value > high)
			return high;
		return value;
	}

	public static double max3(double a, double b, double c) {
		return Math.max(Math.max(a, b), c);
	}

	public static double min3(double a, double b, double c) {
		return Math.min(Math.min(a, b), c);
	}

	public static double sinh(double x) {
		if (Double.isNaN(x))
			return Double.NaN;
		if (Double.isInfinite(x))
			return x > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
		if (x == 0)
			return x;
		return (Math.pow(Math.E, x) - Math.pow(Math.E, -x)) / 2;
	}

	public static double cosh(double x) {
		if (Double.isNaN(x))
			return Double.NaN;
		if (Double.isInfinite(x))
			return Double.POSITIVE_INFINITY;
		if (x == 0)
			return 1.0;
		return (Math.pow(Math.E, x) + Math.pow(Math.E, -x)) / 2;
	}

	public static double tanh(double x) {
		if (Double.isNaN(x))
			return Double.NaN;
		if (x == 0)
			return x;
		if (Double.isInfinite(x))
			return x > 0 ? +1.0 : -1.0;
		return sinh(x) / cosh(x);
	}

	public static int sign(double x) {
		if (x > 0)
			return 1;
		else if (x < 0)
			return -1;
		else
			return 0;
	}

	public static final double DOUBLE_PI = 2 * Math.PI;

	/**
	 * Natural log of 10.
	 */
	public static final double LOG10 = Math.log(10);

	/**
	 * The golden ratio.
	 */
	public static final double PHI = (1 + Math.sqrt(5)) / 2;

	/**
	 * Whether a number is "bad", that is, is NaN or infinite.
	 * 
	 * @param x
	 * @return
	 */
	public static final boolean isBad(double x) {
		return Double.isNaN(x) || Double.isInfinite(x);
	}

	/**
	 * Base-10 logarithm
	 * 
	 * @param x
	 *            A double value.
	 * @return Its base 10 logarithm.
	 */
	public static final double log10(double x) {
		if (x <= 0)
			throw new IllegalArgumentException("log of: " + x);
		return Math.log(x) / LOG10;
	}

	/**
	 * Return min(max(x,low),high). Or in English: clamp the value of x between
	 * low and high.
	 * 
	 * @param x
	 *            An int.
	 * @param low
	 *            The lowest end of the range.
	 * @param high
	 *            The highest end of the range.
	 * @return The value of x "clamped" in the range.
	 */
	public static final int clamp(int x, int low, int high) {
		return Math.max(low, Math.min(x, high));
	}

	/**
	 * Translate degrees to radians.
	 * 
	 * @param a
	 *            An angle in degrees.
	 * @return The same angle in radians.
	 */
	public static final double d2r(double a) {
		return (a / 180) * Math.PI;
	}

	/**
	 * Linear interpolation between two values: computes t*y+(1-t)*x.
	 * 
	 * @param x
	 *            First value.
	 * @param y
	 *            Second value.
	 * @param t
	 *            Interpolation constant.
	 * @return The interpolated value: t*y+(1-t)*x.
	 */
	public static double interpolate(double x, double y, double t) {
		return t * y + (1 - t) * x;
	}

}
