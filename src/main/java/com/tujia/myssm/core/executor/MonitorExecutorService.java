package com.tujia.myssm.core.executor;

import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 扩展 jdk ExecutorService
 *
 * @author: songlinl
 * @create: 2021/09/08 16:00
 */
public interface MonitorExecutorService extends ExecutorService {

    /**
     * core thread num
     */
    int getCorePoolSize();

    /**
     * max thread num
     */
    int getLargestPoolSize();

    /**
     * remaining task num in the queue
     *
     * @return
     */
    int getQueueSize();

    /**
     * the approximate number of threads that are actively executing tasks.
     *
     * @return
     */
    int getActiveCount();

    public static final class TraceWrapper {
        static MonitorExecutorService withMonitor(ThreadPoolExecutor e) {
            Preconditions.checkNotNull(e);
            ExecutorService tWrappedExecutor = e;
            return new MonitorExecutorService() {
                @Override
                public int getCorePoolSize() {
                    return e.getCorePoolSize();
                }

                @Override
                public int getLargestPoolSize() {
                    return e.getLargestPoolSize();
                }

                @Override
                public int getQueueSize() {
                    return e.getQueue().size();
                }

                @Override
                public int getActiveCount() {
                    return e.getActiveCount();
                }

                @Override
                public void execute(Runnable command) {
                    tWrappedExecutor.execute(command);
                }

                @Override
                public void shutdown() {
                    tWrappedExecutor.shutdown();
                }

                @Override
                public List<Runnable> shutdownNow() {
                    return tWrappedExecutor.shutdownNow();
                }

                @Override
                public boolean isShutdown() {
                    return tWrappedExecutor.isShutdown();
                }

                @Override
                public boolean isTerminated() {
                    return tWrappedExecutor.isTerminated();
                }

                @Override
                public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
                    return tWrappedExecutor.awaitTermination(timeout, unit);
                }

                @Override
                public <T> Future<T> submit(Callable<T> task) {
                    return tWrappedExecutor.submit(task);
                }

                @Override
                public <T> Future<T> submit(Runnable task, T result) {
                    return tWrappedExecutor.submit(task, result);
                }

                @Override
                public Future<?> submit(Runnable task) {
                    return tWrappedExecutor.submit(task);
                }

                @Override
                public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
                        throws InterruptedException {
                    if (CollectionUtils.isEmpty(tasks)) {
                        return Collections.emptyList();
                    }
                    return tWrappedExecutor.invokeAll(tasks);
                }

                @Override
                public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout,
                                                     TimeUnit unit) throws InterruptedException {
                    if (CollectionUtils.isEmpty(tasks)) {
                        return Collections.emptyList();
                    }
                    return tWrappedExecutor.invokeAll(tasks, timeout, unit);
                }

                @Override
                public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
                        throws InterruptedException, ExecutionException {
                    if (CollectionUtils.isEmpty(tasks)) {
                        return null;
                    }
                    return tWrappedExecutor.invokeAny(tasks);
                }

                @Override
                public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
                        throws InterruptedException, ExecutionException, TimeoutException {
                    if (CollectionUtils.isEmpty(tasks)) {
                        return null;
                    }
                    return tWrappedExecutor.invokeAny(tasks, timeout, unit);
                }
            };
        }
    }


}
