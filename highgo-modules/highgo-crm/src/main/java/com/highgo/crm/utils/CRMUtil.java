package com.highgo.crm.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * CRM 定制的工具类
 */
public class CRMUtil {
    /**
     * 对象转List集合
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}
