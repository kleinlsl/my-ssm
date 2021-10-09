package com.tujia.myssm.test;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 *
 * @author: songlinl
 * @create: 2021/10/08 16:44
 */
public class testScheduledThreadPool {

    @Test
    public void testBug() {
        Scanner scanner = new Scanner(System.in);
        new ScheduledThreadPoolExecutor(1,
                new ThreadFactoryBuilder().setNameFormat("need-sync-cash-back-monitor-%d").build())
                .scheduleWithFixedDelay(() -> {
                    System.out.println("before: System.currentTimeMillis() = " + System.currentTimeMillis());
                    List<String> list = null;
                    list.get(0);
                    System.out.println("after: System.currentTimeMillis() = " + System.currentTimeMillis());
                }, 5, 10, TimeUnit.SECONDS);
        scanner.next();
    }
}
