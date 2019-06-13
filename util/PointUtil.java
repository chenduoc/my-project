package com.chen.my_project.util;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 计算点的工具
 * 
 * @author ChenDuochuang
 * @date 2018年11月7日
 */
public final class PointUtil {

    private PointUtil() {
    }

    /**
     * 判断点是否在圆形内
     * @param pointLon 要判断的纵坐标
     * @param pointLat 要判断的横坐标
     * @param lon 圆心纵坐标
     * @param lat 圆心横坐标
     * @param radius 圆半径
     * @author ChenDuochuang
     */
    public static boolean isInCircle(double pointLon, double pointLat, double lon, double lat, String radius) {

        // 求两点之间的距离，比较两点间距离和圆半径
        double distance = Math.hypot((pointLon - lon), (pointLat - lat));
        double r = Double.parseDouble(radius);

        return distance < r;
    }

    /**
     * 根据经纬度判断点是否在圆形内
     * @param pointLon 要判断的纵坐标
     * @param pointLat 要判断的横坐标
     * @param lon 圆心纵坐标
     * @param lat 圆心横坐标
     * @param radius 圆半径
     * @author ChenDuochuang
     */
    public static boolean isInCircleByLonLat(double pointLon, double pointLat, double lon, double lat, String radius) {

        // 求两点之间的距离，比较两点间距离和圆半径
        double distance = Double.parseDouble(DistanceUtil.getDistance(pointLon, pointLat, lon, lat));
        double r = Double.parseDouble(radius);
        System.out.println("++distance:" + distance);
        System.out.println("++radius:" + r);

        return distance < r;
    }

    /**
     * 判断点是否在矩形内
     * @param pointLon 要判断的纵坐标
     * @param pointLat 要判断的横坐标
     * @param southWestLon 西南点纵坐标
     * @param southWestLat 西南点横坐标
     * @param northEastLon 东北点纵坐标
     * @param northEastLat 东北点横坐标
     * @return
     * @author ChenDuochuang
     */
    public static boolean isInRectangle(double pointLon, double pointLat, double southWestLon, double southWestLat,
            double northEastLon, double northEastLat) {
        return pointLat > southWestLat && pointLat < northEastLat && pointLon > southWestLon && pointLon < northEastLon;
    }

    /**
     * 判断点是否在多边形内
     * @param pointLon 要判断的纵坐标
     * @param pointLat 要判断的横坐标
     * @param pointInfo 坐标点的字符串，用逗号分隔， 例如："lon1,lat1,lon2,lat2..."
     * @return
     * @author ChenDuochuang
     */
    public static boolean isInPolygon(double pointLon, double pointLat, String pointInfo) {

        // 将要判断的横纵坐标组成一个点
        Point2D.Double point = new Point2D.Double(pointLon, pointLat);
        // 将pointInfo组成点的集合
        String[] pointArr = pointInfo.split(",");
        List<Point2D.Double> points = new ArrayList<Point2D.Double>();
        for (int i = 0; i < pointArr.length; i = i + 2) {
            Point2D.Double polygonPoint = new Point2D.Double(Double.parseDouble(pointArr[i]),
                    Double.parseDouble(pointArr[i + 1]));
            points.add(polygonPoint);
        }

        return checkPoint(point, points);
    }

    /**
     * 判断点是否在多边形内
     * @param point
     * @param points
     * @return
     * @author ChenDuochuang
     */
    public static boolean checkPoint(Point2D.Double point, List<Point2D.Double> points) {
        GeneralPath generalPath = new GeneralPath();

        // 设定起点
        Point2D.Double firstPoint = points.get(0);
        // 通过移动到指定坐标，将一个点添加到路径中
        generalPath.moveTo(firstPoint.x, firstPoint.y);
        points.remove(0);

        for (Point2D.Double p : points) {
            // 绘制一条从当前坐标到指定坐标的直线
            generalPath.lineTo(p.x, p.y);
        }

        // 将多边形封闭
        generalPath.lineTo(firstPoint.x, firstPoint.y);
        generalPath.closePath();

        // 测试点是否在多边形的边界内
        return generalPath.contains(point);
    }

    /**
     * 主方法
     * @param args
     * @author ChenDuochuang
     */
    public static void main(String[] args) {

        double pointLon = 123.481961;
        double pointLat = 41.708428;
        double circleLon = 123.481961;
        double circleLat = 41.695809;
        String radius = "200";

        boolean result = isInCircleByLonLat(pointLon, pointLat, circleLon, circleLat, radius);
        System.out.println("++result:" + result);

    }
}
