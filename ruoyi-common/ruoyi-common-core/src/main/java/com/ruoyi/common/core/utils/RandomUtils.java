package com.ruoyi.common.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;

public class RandomUtils {
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static String generateString(int paramInt) {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (byte b = 0; b < paramInt; b++)
            stringBuffer.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyz".length())));
        return stringBuffer.toString();
    }

    public static String generateUpperString(int paramInt) {
        return generateString(paramInt).toUpperCase();
    }

    public static String generateDataString(int paramInt) {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (byte b = 0; b < paramInt; b++)
            stringBuffer.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyz".length())));
        String str = "yyyyMMddHHmmssSSS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        Calendar calendar = Calendar.getInstance();
        stringBuffer.append(simpleDateFormat.format(calendar.getTime()));
        return stringBuffer.toString();
    }

    public static String[] generateAryString(int paramInt1, int paramInt2) {
        HashSet<String> hashSet = new HashSet();
        while (hashSet.size() < paramInt1)
            hashSet.add(generateString(paramInt2));
        return hashSet.<String>toArray(new String[0]);
    }
}
