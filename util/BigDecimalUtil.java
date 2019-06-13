package com.chen.my_project.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 浮点数计算工具类
 * @author ChenDuochuang
 * @date 2018年12月7日
 */
public final class BigDecimalUtil {

	// ROUND_UP：远离零方向舍入。向绝对值最大的方向舍入，只要舍弃位非0即进位。
	// ROUND_DOWN：趋向零方向舍入。向绝对值最小的方向输入，所有的位都要舍弃，不存在进位情况。
	// ROUND_CEILING：向正无穷方向舍入。向正最大方向靠拢。若是正数，舍入行为类似于ROUND_UP，若为负数，舍入行为类似于ROUND_DOWN。Math.round()方法就是使用的此模式。
	// ROUND_FLOOR：向负无穷方向舍入。向负无穷方向靠拢。若是正数，舍入行为类似于ROUND_DOWN；若为负数，舍入行为类似于ROUND_UP。
	// HALF_UP：最近数字舍入(5进)。这是我们最经典的四舍五入。
	// HALF_DOWN：最近数字舍入(5舍)。在这里5是要舍弃的。
	// HAIL_EVEN：银行家舍入法。

	/**
	 * 俩数相加
	 * @param value1
	 * @param value2
	 * @return
	 * @author ChenDuochuang
	 */
	public static double add(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.add(b2).doubleValue();
	}

	/**
	 * 俩数相加，按条件保留几位小数
	 * @param value1
	 * @param value2
	 * @param scale 保留几位小数
	 * @return
	 * @author ChenDuochuang
	 */
	public static double add(double value1, double value2, int scale) {
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.add(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 俩数相减
	 * @param value1
	 * @param value2
	 * @return
	 * @author ChenDuochuang
	 */
	public static double subtract(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 俩数相减，按条件保留几位小数
	 * @param value1
	 * @param value2
	 * @param scale 保留几位小数
	 * @return
	 * @author ChenDuochuang
	 */
	public static double subtract(double value1, double value2, int scale) {
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.subtract(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 俩数相乘
	 * @param value1
	 * @param value2
	 * @return
	 * @author ChenDuochuang
	 */
	public static double multiply(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 俩数相乘，按条件保留几位小数
	 * @param value1
	 * @param value2
	 * @param scale 保留几位小数
	 * @return
	 * @author ChenDuochuang
	 */
	public static double multiply(double value1, double value2, int scale) {
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.multiply(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 俩数相除
	 * @param value1
	 * @param value2
	 * @return
	 * @author ChenDuochuang
	 */
	public static double divide(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.divide(b2).doubleValue();
	}

	/**
	 * 俩数相除，按条件保留几位小数
	 * @param value1
	 * @param value2
	 * @param scale 保留几位小数
	 * @return
	 * @author ChenDuochuang
	 */
	public static double divide(double value1, double value2, int scale) {
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.divide(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}
}
