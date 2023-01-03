package com.highgo.medium.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class SSHLinuxUtil {

    public static String exeCommand(String host, int port, String user, String password, String command) {
        Session session = null;
        ChannelExec channelExec = null;
        String out = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setConfig("StrictHostKeyChecking", "no");

            session.setPassword(password);
            session.connect();
            channelExec = (ChannelExec) session.openChannel("exec");
            InputStream in = channelExec.getInputStream();
            channelExec.setCommand(command);
            channelExec.setErrStream(System.err);
            channelExec.connect();
            out = IOUtils.toString(in, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channelExec.disconnect();
            session.disconnect();
        }
        return out;
    }

}
