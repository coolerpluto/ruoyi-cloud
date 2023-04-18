package com.highgo.crm.domain;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Soldier implements Runnable
{
    private String soldier;
    private CyclicBarrier cyclicBarrier;

    public Soldier(CyclicBarrier cyclicBarrier, String soldierName)
    {
        this.cyclicBarrier = cyclicBarrier;
        this.soldier = soldierName;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println(soldier + " 等待集合中。。。");
            cyclicBarrier.await();
            System.out.println(soldier + " 完成中。。。");
            doFire();
            cyclicBarrier.await();
        }
        catch (InterruptedException | BrokenBarrierException e)
        {
            e.printStackTrace();
        }
    }

    private void doFire()
    {
        try
        {
            Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            System.out.println(soldier + ":完成任务");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}