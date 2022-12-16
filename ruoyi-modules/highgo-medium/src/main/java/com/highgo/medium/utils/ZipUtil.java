package com.highgo.medium.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具
 */
public class ZipUtil {
    private static final int BUFFER_SIZE = 2 * 1024;

    public static void zipStream(Map<String, InputStream> inputStreamMap, OutputStream out) {
        ZipOutputStream zipOutputStream = null;

        try {
            zipOutputStream = new ZipOutputStream(out);
            for (Map.Entry<String, InputStream> inputStream : inputStreamMap.entrySet()) {
                String fileName = inputStream.getKey();
                InputStream fileInputStream = inputStream.getValue();

                byte[] buf = new byte[BUFFER_SIZE];
                zipOutputStream.putNextEntry(new ZipEntry(fileName));

                int len;
                while ((len = fileInputStream.read(buf,0,BUFFER_SIZE)) != -1) {
                    zipOutputStream.write(buf, 0, len);
                }
                zipOutputStream.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

