package com.highgo.crm.domain;

public class BarrierRun implements Runnable
{
    boolean flag;
    int n;

    public BarrierRun(boolean flag, int n)
    {
        this.flag = flag;
        this.n = n;
    }

    @Override
    public void run()
    {
        if (flag)
        {
            System.out.println(n + "个完成任务");
        }
        else
        {
            System.out.println(n + "个集合完毕");
            flag = true;
        }
    }
}