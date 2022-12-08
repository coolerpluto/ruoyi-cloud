package com.ruoyi.file.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class FileReq implements Serializable {
    private static final long serialVersionUID = 1L;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFullFileName() {
        String fullPath = "";
        if (StringUtils.isBlank(fullFileName)) {
            if (!StringUtils.endsWith(filePath, "/") && !StringUtils.startsWith(fileName, "/")) {
                filePath = filePath + "/";
            }
            fullPath = filePath + fileName;
        } else {
            fullPath = fullFileName;
        }
        return fullPath;
    }

    public void setFullFileName(String fullFileName) {
        this.fullFileName = fullFileName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    private String fileName = "default";
    private String filePath;
    private String fullFileName;
    private String bucketName = "default";

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("fileName", getFileName())
                .append("filePath", getFilePath())
                .append("fullFileName", getFullFileName())
                .append("bucketName", getBucketName())
                .toString();
    }
}
