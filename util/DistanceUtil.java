package com.chen.my_project.util;

import java.text.DecimalFormat;

public final class DistanceUtil {

	/**
	 * 地球半径（km）
	 */
	private static final double EARTH_RADIUS_KM = 6378.137;

	private DistanceUtil() {
	};

	/**
	 * 角度转换成弧度
	 * @param d
	 * @return
	 * @author ChenDuochuang
	 */
	private static double rad(double d) {
		return d * Math.PI / 180.00;
	}

	/**
	 * 根据经纬度计算两点之间的距离（单位米）
	 * @param longitude1
	 * @param latitude1
	 * @param longitude2
	 * @param latitude2
	 * @return
	 * @author ChenDuochuang
	 */
	public static String getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
		double Lat1 = rad(latitude1); // 纬度
		double Lat2 = rad(latitude2);
		double a = Lat1 - Lat2;// 两点纬度之差
		double b = rad(longitude1) - rad(longitude2); // 经度之差
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(Lat1) * Math.cos(Lat2)
				* Math.pow(Math.sin(b / 2), 2)));// 计算两点距离的公式
		s = s * EARTH_RADIUS_KM * 1000;// 弧长乘地球半径（半径为米）
		s = Math.round(s * 10000d) / 10000d;// 精确距离的数值
		// s = s / 1000;// 将单位转换为km，如果想得到以米为单位的数据 就不用除以1000
		// 四舍五入 保留一位小数
		DecimalFormat df = new DecimalFormat("#.0");
		return df.format(s);
	}

	/**
     * 单位转换：米转换千米
     * @param distance
     * @return
     * @author ChenDuochuang
     */
    public static String meterToKiloMeter(String distance) {
        double meter = Double.parseDouble(distance);
        meter = meter / 1000;
        // 四舍五入 保留一位小数
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(meter);
    }
	
	/**
	 * 主方法
	 * @param args
	 * @author ChenDuochuang
	 */
	public static void main(String[] args) {
		// 根据两点间的经纬度计算距离，单位：m
		String s = getDistance(103.6799236520451, 30.643990443627033, 123.48205593532987, 41.696851399739586);
		System.out.println(s);
		System.out.println(meterToKiloMeter(s));
	}
}
