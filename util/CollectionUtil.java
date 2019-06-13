package com.chen.my_project.util;

import java.util.Iterator;
import java.util.List;

/**
 * 集合工具类
 * @author ChenDuochuang
 * @date 2019年6月13日
 */
public final class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * 移除列表内元素
     * @param list
     * @author ChenDuochuang
     */
    public static void removeMember(List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String value = it.next();
            if ("0".equals(value)) {
                it.remove();
            }
        }
    }
}
