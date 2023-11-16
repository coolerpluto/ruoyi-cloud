package com.ruoyi.common.core.utils.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * SSH连接Linux的账户
 */
public class SSHLinuxUtils
{
    public static String exeCommand(String host, int port, String user, String password, String command)
    {
        Session session = null;
        ChannelExec channelExec = null;
        String out = null;
        try
        {
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
            out = IOUtils.toString(in, StandardCharsets.UTF_8);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != channelExec)
            {
                channelExec.disconnect();

            }
            if (null != session)
            {
                session.disconnect();
            }
        }
        return out;
    }
}
