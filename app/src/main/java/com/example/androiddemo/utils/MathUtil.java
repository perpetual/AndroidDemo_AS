package com.example.androiddemo.utils;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2015-4-26		Create		
 * </pre>
 */
public class MathUtil {

	public static double getScaleDoubleValue(boolean sign, double... args) {
		double sum = 0;
		for (double d : args) {
			sum += Math.pow(d, 2);
		}
		return (sign ? getSign(args) : 1) * Math.sqrt(sum);
	}
	
	public static float getScaleFloatValue(boolean sign, double... args) {
		return (float)getScaleDoubleValue(sign, args);
	}
	
	public static int getSign(double... args) {
		int sign = 1;
		for (double d : args) {
			if (d < 0) {
				sign *= -1;
			}
		}
		return sign;
	}
}

