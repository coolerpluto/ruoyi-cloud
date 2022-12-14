package com.highgo.medium.utils;

import java.text.SimpleDateFormat;

/**
 * 介质工具类
 */
public class MediumUtil {
    private static volatile int Guid = 100;

    /**
     * 获取20位long yyyyMM+new Date().getTime()
     *
     * @return
     */
    public static long getId() {
        MediumUtil.Guid += 1;
        long now = System.currentTimeMillis();
        //获取4位年月份数字
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        //获取时间戳
        String time = dateFormat.format(now);
        String info = now + "";

        //获取三位随机数
        int ran = 0;
        if (MediumUtil.Guid > 999) {
            MediumUtil.Guid = 100;
        }
        ran = MediumUtil.Guid;
        String resStr = time + info.substring(2, info.length()) + ran;
        return Long.valueOf(resStr);
    }
}
