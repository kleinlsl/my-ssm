package com.tujia.myssm.core.executor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池包装
 *
 * @author: songlinl
 * @create: 2021/09/08 16:16
 */
@Slf4j
public class EM {
    public static final MonitorExecutorService defaultExecutor;

    static {
        defaultExecutor = MonitorExecutorService.TraceWrapper.withMonitor(
                new ThreadPoolExecutor(4, 4, 0L,
                        TimeUnit.NANOSECONDS, new LinkedBlockingQueue<>(100),
                        new ThreadFactoryBuilder().setNameFormat("system_default_%d").build())
        );
    }


    public static void main(String[] args) {
        final ExecutorService EXECUTOR = EM.defaultExecutor;
        Map<Integer, Future<List<String>>> futureMap = Maps.newHashMap();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Future<List<String>> listFuture = EXECUTOR.submit(() -> {
                try {
                    List<String> stringList = Lists.newArrayList();
                    stringList.add(String.valueOf(finalI));
                    if (finalI == 5) {
                        throw new RuntimeException("出异常了");
                    }
                    return stringList;
                } catch (Exception e) {
                    // TODO: 2021/9/8 异常怎么返回空的Future
                    log.error("[WxAccountDrainageBiz.queryMemberReceived] 异步查卷异常", e);
                    return null;
                }
            });
            futureMap.put(i, listFuture);
        }
        for (int i = 0; i < 10; i++) {
            try {
                List<String> stringList = futureMap.get(i).get(10, TimeUnit.MILLISECONDS);
                System.out.println("stringList = " + stringList);
            } catch (Exception e) {
                log.error("异常:{}", i, e);
            } finally {
                futureMap.get(i).cancel(true);
            }
        }

    }
}
