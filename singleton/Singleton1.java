package com.chen.my_project.singleton;

/**
 * <p>
 * 单例模式（只适合单线程环境）
 * </p>
 * 
 * <pre>
 * 注解:Singleton的静态属性instance中，只有instance为null的时候才创建一个实例，构造函数私有，确保每次都只创建一个，避免重复创建。
 * </pre>
 * 
 * <pre>
 * 缺点：只在单线程的情况下正常运行，在多线程的情况下，就会出问题。
 * 例如：当两个线程同时运行到判断instance是否为空的if语句，并且instance确实没有创建好时，那么两个线程都会创建一个实例。
 * </pre>
 * 
 * @author ChenDuochuang
 * @date 2018年11月12日
 */
public class Singleton1 {

    private static Singleton1 instance = null;

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }

}
