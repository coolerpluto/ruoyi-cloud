package com.ruoyi.common.core.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具
 */
public class ZipUtils
{
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩流
     *
     * @param inputStreamMap key val的形式 key为文件名 val为文件输入流
     * @param out            文件输出流
     */
    public static void zipStream(Map<String, InputStream> inputStreamMap, OutputStream out)
    {
        ZipOutputStream zipOutputStream = null;
        try
        {
            zipOutputStream = new ZipOutputStream(out);
            for (Map.Entry<String, InputStream> inputStream : inputStreamMap.entrySet())
            {
                String fileName = inputStream.getKey();
                InputStream fileInputStream = inputStream.getValue();
                byte[] buf = new byte[BUFFER_SIZE];
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                int len;
                while ((len = fileInputStream.read(buf, 0, BUFFER_SIZE)) != -1)
                {
                    zipOutputStream.write(buf, 0, len);
                }
                zipOutputStream.closeEntry();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (zipOutputStream != null)
            {
                try
                {
                    zipOutputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 目录压缩
     *
     * @param folderPath   要压缩的目录
     * @param zos          压缩输出流
     * @param parentFolder 父级目录 不以 / 结尾
     * @throws IOException 异常
     */
    public static void addFolderToZip(String folderPath, ZipOutputStream zos, String parentFolder) throws IOException
    {
        File folder = new File(folderPath);
        String folderName = folder.getName();
        if (!parentFolder.equals(""))
        {
            folderName = parentFolder + "/" + folderName;
        }
        ZipEntry zipEntry = new ZipEntry(folderName + "/");
        zos.putNextEntry(zipEntry);
        for (File file : folder.listFiles())
        {
            if (file.isFile())
            {
                addFileToZip(file, zos, folderName);
            }
            else
            {
                addFolderToZip(file.getAbsolutePath(), zos, folderName);
            }
        }
    }

    /**
     * 文件压缩
     *
     * @param file         要压缩的文件
     * @param zos          压缩输出流
     * @param parentFolder 父级目录 不以 / 结尾
     * @throws IOException 异常
     */
    public static void addFileToZip(File file, ZipOutputStream zos, String parentFolder) throws IOException
    {
        String fileName = file.getName();
        if (!parentFolder.equals(""))
        {
            fileName = parentFolder + "/" + fileName;
        }
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0)
        {
            zos.write(buffer, 0, length);
        }
        fis.close();
    }
}
