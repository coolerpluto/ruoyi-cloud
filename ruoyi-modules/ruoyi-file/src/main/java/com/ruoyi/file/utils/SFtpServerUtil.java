package com.ruoyi.file.utils;

import com.jcraft.jsch.*;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.file.config.SFtpServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Ftp 服务器 工具类
 */
@Component
public class SFtpServerUtil {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SFtpServerConfig ftpServerConfig;

    private static SFtpServerUtil instance = null;

    private SFtpServerUtil() {
    }

    //初始化 为了使Autowired注入的生效对象生效
    @PostConstruct
    public void init() {
        instance = this;
    }

    public static SFtpServerUtil getInstance() {
        if (instance == null) {
            if (instance == null) {
                instance = new SFtpServerUtil();
            }
        }
        return instance;
    }

    /**
     * 创建 Session 连接
     *
     * @return 返回session
     */
    public Session createSession() throws Exception {

        Session session;
        JSch jsch = new JSch();
        if (instance.ftpServerConfig.getPort() <= 0) {
            session = jsch.getSession(instance.ftpServerConfig.getAccessKey(), instance.ftpServerConfig.getIp());
        } else {
            session = jsch.getSession(instance.ftpServerConfig.getAccessKey(), instance.ftpServerConfig.getIp(), instance.ftpServerConfig.getPort());
        }
        if (session == null) {
            throw new Exception("获取session失败：" + instance.ftpServerConfig.getIp());
        }
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");

        session.setConfig(sshConfig);

        session.setPassword(instance.ftpServerConfig.getSecretKey());
        session.connect();

        log.debug(instance.ftpServerConfig.getIp() + "Session已经完成连接");
        return session;
    }


    /**
     * 关闭session连接
     *
     * @param session 要关闭的对象
     */
    public void closeSession(Session session) {
        if (null == session) {
            return;
        }
        if (session.isConnected()) {
            return;
        }
        session.disconnect();
    }

    /**
     * 关闭 channel 通道
     *
     * @return
     */
    public void closeChannel(Channel channel) {
        if (channel == null) {
            return;
        }
        if (channel.isConnected()) {
            channel.disconnect();
        } else if (channel.isClosed()) {
            System.out.println("已经关闭");
            log.debug("SFTP 连接已经完成关闭");
        }
        try {
            closeSession(channel.getSession());
        } catch (JSchException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建 SFTP 通道
     *
     * @return 返回通道
     */
    public ChannelSftp getSftpChannel(Session sshSession) {
        ChannelSftp sftp = null;
        try {
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            log.debug(instance.ftpServerConfig.getProtocol() + "连接通道已经完成构建");

            sftp = (ChannelSftp) channel;
            sftp.cd(instance.ftpServerConfig.getHomeDir());
        } catch (Exception e) {
            closeChannel(sftp);
            throw new RuntimeException(e);
        }
        return sftp;
    }

    /**
     * 获取 执行命令 通道
     *
     * @param session    连接信息
     * @param commandStr 命令
     * @return 返回通道
     */
    public ChannelExec getExecChannel(Session session, String commandStr) {
        ChannelExec sftp;
        try {
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(commandStr);
            channel.connect();
            sftp = (ChannelExec) channel;
            return sftp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 执行 liunx 命令
     *
     * @param command 命令内容
     * @return 命令输出
     */
    private String execute(String command) {
        StringBuilder strBuffer = new StringBuilder();
        try {
            Session session = createSession();
            ChannelExec channelExec = getExecChannel(session, command);
            BufferedReader input = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));

            log.debug("命令: {}", command);
            // 获取命令的输出
            String line;
            while ((line = input.readLine()) != null) {
                strBuffer.append(line);
            }
            input.close();
            closeSession(session);
            closeChannel(channelExec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strBuffer.toString();
    }

    /**
     * 压缩文件夹为ZIP
     *
     * @param fileDir        文件路径
     * @param fileName       文件名称
     * @param additionalName 压缩附加名
     * @return 压缩结果
     */
    public boolean zipDir(String fileDir, String fileName, String additionalName) {
        try {
            String execute = execute("cd " + fileDir + "\n" +
                    "zip  -r " + fileDir + "/" + additionalName + ".zip " + fileName);
            System.out.println(execute);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断目录是否存在
     *
     * @param directory 目录
     * @return 存在结果
     */
    public boolean isDirExist(String directory, ChannelSftp sftp) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = sftpATTRS.isDir();
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return isDirExistFlag;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public boolean delete(String directory, String deleteFile) {
        boolean res = true;
        Session session = null;
        ChannelSftp sftp = null;
        try {
            session = createSession();
            sftp = getSftpChannel(session);

            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            res = false;
            log.error("文件删除失败:{}", e.getMessage());
        }finally {
            closeChannel(sftp);
            closeSession(session);
        }
        return res;
    }

    /**
     * 创建目录
     * 循环建立目录
     *
     * @param createPath 目录路径
     * @return 返回是不是创建完成
     */
    public boolean createDir(String createPath, ChannelSftp sftp) {
        try {
            if (isDirExist(createPath, sftp)) {
                sftp.cd(createPath);
                return true;
            }
            String[] pathArray = createPath.split("/");
            StringBuilder filePath = new StringBuilder("/");
            for (String path : pathArray) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path).append("/");
                if (!isDirExist(filePath.toString(), sftp)) {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                }
                // 进入并设置为当前目录
                sftp.cd(filePath.toString());
            }
            sftp.cd(createPath);
            return true;
        } catch (SftpException e) {
            closeChannel(sftp);
            log.error("创建路径失败:{}", e.getMessage());
        }
        return false;
    }

    /**
     * 创建文件夹
     * 在工作目录下的 runoob2 目录中，建立一个名为 test 的子目录。
     * 若 runoob2 目录原本不存在，则建立一个。（注：本例若不加 -p 参数，且原本 runoob2 目录不存在，则产生错误。）
     * mkdir -p runoob2/test
     *
     * @param fileDir 文件夹路径
     * @return 创建结果
     */
    public boolean createFileDir(String fileDir) {
        try {
            execute("mkdir -p " + fileDir + "");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 附件上传
     *
     * @param fileName    文件名
     * @param inputStream 文件流
     */
    public String upload(String fileName, InputStream inputStream) throws Exception {
        return upload(null, fileName, inputStream);
    }

    /**
     * 文件上传
     *
     * @param relativePath 文件保存的相对路径(最后一级目录)
     * @param fileName     文件名
     * @param inputStream  文件流
     */
    public String upload(String relativePath, String fileName, InputStream inputStream) throws Exception {
        Session session = createSession();
        ChannelSftp sftpChannel = getSftpChannel(session);
        createDir(instance.ftpServerConfig.getHomeDir(), sftpChannel);
        String filePath = instance.ftpServerConfig.getHomeDir();
        String pathChild = "";
        if (relativePath != null && !relativePath.trim().isEmpty()) {
            pathChild = relativePath + "/";
        }
        pathChild = pathChild + DateUtils.datePath();
        filePath = filePath + "/" + pathChild;
        createFileDir(filePath);
        filePath = filePath + "/" + fileName;
        try {
            sftpChannel.put(inputStream, filePath);
        } finally {
            closeChannel(sftpChannel);
            closeSession(session);
        }
        return filePath.substring(instance.ftpServerConfig.getHomeDir().length() + 1);
    }

    /**
     * 文件下载
     *
     * @param response 返回流
     * @param filePath 文件路径 不带名称
     * @param fileName 文名名
     */
    public void download(HttpServletResponse response, String filePath, String fileName) throws Exception {
        Session session = createSession();
        ChannelSftp sftp = getSftpChannel(session);
        response.setContentType("application/octet-stream");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));// 设置文件名

        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        InputStream inputStream = null;
        System.out.println("下载文件流:准备：fileName:"+fileName+":"+System.currentTimeMillis());
        try {
            sftp.cd(instance.ftpServerConfig.getHomeDir());
            sftp.cd(filePath);
            inputStream = sftp.get(fileName);
            bis = new BufferedInputStream(inputStream);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("下载文件流:完成：fileName:"+fileName+":"+System.currentTimeMillis());
        } catch (Exception e) {
            System.out.println("下载文件流:异常：fileName:"+fileName+":"+System.currentTimeMillis());
            e.printStackTrace();
        } finally { // 做关闭操作
            System.out.println("下载文件流:完成 关闭文件流准备：fileName:"+fileName+":"+System.currentTimeMillis());
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            closeChannel(sftp);
            closeSession(session);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载二进制流
     *
     * @param filePath 文件路径 不带名称
     * @param fileName 文名名
     */
    public byte[] download(String filePath, String fileName) {
        Session session = null;
        ChannelSftp sftp = null;
        byte[] res = null;
        try {
            session = createSession();
            sftp = getSftpChannel(session);
            sftp.cd(instance.ftpServerConfig.getHomeDir());
            sftp.cd(filePath);
            InputStream inputStream = sftp.get(fileName);
            res = MinioUtil.getInstance().inputStreamToByteArray(inputStream);
            inputStream.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeChannel(sftp);
            closeSession(session);
        }
        return res;
    }
}
