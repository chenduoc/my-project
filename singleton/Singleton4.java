package com.chen.my_project.singleton;

/**
 * <p>
 * 单例模式（饿汉式）
 * </p>
 * 
 * <pre>
 * 注解：初试化静态的instance创建一次。如果我们在Singleton类里面写一个静态的方法不需要创建实例，它仍然会早早的创建一次实例。而降低内存的使用率。
 * </pre>
 * 
 * <pre>
 * 缺点：没有lazy loading的效果，从而降低内存的使用率。
 * </pre>
 * 
 * @author ChenDuochuang
 * @date 2018年11月12日
 */
public class Singleton4 {

    private static Singleton4 instance = new Singleton4();

    private Singleton4() {
    }

    public static Singleton4 getInstance() {
        return instance;
    }

}
