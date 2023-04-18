package com.highgo.crm;

import com.highgo.crm.domain.BarrierRun;
import com.highgo.crm.domain.Soldier;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo
{
    public static void main(String[] args)
    {
        int n = 10;
        Thread[] allSoldier = new Thread[n];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(n, new BarrierRun(flag, n));
        System.out.println("集合");
        for (int i = 0; i < n; i++)
        {
            System.out.println("士兵" + (i + 1) + "报道");
            allSoldier[i] = new Thread(new Soldier(cyclic, "士兵" + (i + 1)));
            allSoldier[i].start();
        }
    }
}
