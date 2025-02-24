package com.tujia.myssm.demo.executor;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.Uninterruptibles;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 *
 * @author: songlinl
 * @create: 2023/5/12 11:03
 */
@Slf4j
public class InvokerAll {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setNameFormat("batchUnitForDetail_query_%d").build());
    private static ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executor);

    public static void main(String[] args)  {
        long sleepTime = 1000;
        long timeout = 10000;
        demo1(sleepTime, timeout, 20);

        demo2(sleepTime, timeout, 20);
    }

    public static void demo1(long sleepTime, long timeout, int num) {
        //多线程执行请求
        List<Callable<Integer>> calNumWorks = Lists.newArrayList();

        for (int i = 0; i < num; i++) {
            calNumWorks.add(new CalNumWork(i, sleepTime * i));
        }

        List<ListenableFuture<Integer>> listenableFutureList = calNumWorks.stream().map(works -> listeningExecutor.submit(works)).collect(Collectors.toList());
        //        List<Future<Integer>> listenableFutureList = listeningExecutor.invokeAll(calNumWorks, timeout, TimeUnit.MILLISECONDS);
        System.out.println("invokeAll ");
        //        // 获取数据
        List<Integer> result = Lists.newArrayListWithExpectedSize(listenableFutureList.size());
        try {
            result = Uninterruptibles.getUninterruptibly(Futures.successfulAsList(listenableFutureList), timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            log.error("TmonitorConstants.GET_CTRIP_DIRECT_MULITY_THREAD_READ_TIME_OUT timeout:{}", timeout, e);
        } catch (Exception e) {
            log.error("TmonitorConstants.GET_CTRIP_DIRECT_MULITY_THREAD_READ_ERROR", e);
        }
        result = MoreObjects.firstNonNull(result, Lists.newArrayList());
        result = result.stream().filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println("result = " + result);
    }

    public static void demo2(long sleepTime, long timeout, int num) {
        //多线程执行请求
        List<Callable<Integer>> calNumWorks = Lists.newArrayList();

        for (int i = num; i > 0; i--) {
            calNumWorks.add(new CalNumWork(i, sleepTime * i));
        }

        List<Future<Integer>> listenableFutureList = Lists.newArrayListWithExpectedSize(num);
        try {
            listenableFutureList = listeningExecutor.invokeAll(calNumWorks, timeout, TimeUnit.MILLISECONDS);
            log.info("invokeAll end");
        } catch (Exception e) {
            log.error("invokeAll error", e);
        }
        //        // 获取数据
        List<Integer> result = Lists.newArrayListWithExpectedSize(listenableFutureList.size());

        for (Future<Integer> f : listenableFutureList) {
            try {
                if (f.isDone() && !f.isCancelled()) {
                    result.add(f.get());
                }
            } catch (Exception e) {
                log.error("future get is error", e);
            }
        }
        result = MoreObjects.firstNonNull(result, Lists.newArrayList());
        //        result = result.stream().filter(Objects::nonNull).collect(Collectors.toList());
        log.info("result = " + result);
    }

    private static class CalNumWork implements Callable<Integer> {
        /**
         * 查询直连报价请求体
         */
        private int workId;
        private long sleepTime;

        public CalNumWork(int workId, long sleepTime) {
            this.workId = workId;
            this.sleepTime = sleepTime;
        }

        @Override
        public Integer call() throws Exception {
            try {
                Preconditions.checkArgument(workId >= 0, "workId is error");
                Thread.sleep(sleepTime);
                log.info("id:{}, sleepTime:{}", workId, sleepTime);
                return workId;
            } catch (Exception e) {
                log.error("id:{}, sleepTime:{}", workId, sleepTime, e);
                return -1;
            }
        }
    }

}
