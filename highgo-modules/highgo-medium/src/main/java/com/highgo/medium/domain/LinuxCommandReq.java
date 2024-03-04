package com.highgo.medium.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * 命令对象
 */
public class LinuxCommandReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 依次执行多个命令: 0 --> N
     */
    private List<String> commandStrList;
    /**
     * 命令所在的服务器的位置-提前将命令放置到服务器
     */
    private String commandDir;
    /**
     * 执行命令时所在的路径
     */
    private String execHome;

    public List<String> getCommandStrList() {
        return commandStrList;
    }

    public void setCommandStrList(List<String> commandStrList) {
        this.commandStrList = commandStrList;
    }

    public String getCommandDir() {
        return commandDir;
    }

    public void setCommandDir(String commandDir) {
        this.commandDir = commandDir;
    }

    public String getExecHome() {
        return execHome;
    }

    public void setExecHome(String execHome) {
        this.execHome = execHome;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("execHome", getExecHome())
                .append("commandDir", getCommandDir())
                .append("commandStrList", getCommandStrList())
                .toString();
    }
}

