package ch.qos.logback.core.rolling;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @author songlinl
 */
public class TjRollingFileAppender<E> extends RollingFileAppender<E> {
    private static final Logger LOG = LoggerFactory.getLogger(TjRollingFileAppender.class);

    private static final List<TjRollingFileAppender> APPENDERS = Lists.newArrayList();

    static {
        new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder().setNameFormat("TuJia-Logback-Timer-monitor-%d")
                                                                     .build()).scheduleWithFixedDelay(() -> {
            try {
                for (TjRollingFileAppender appender : APPENDERS) {
                    appender.checkRollover();
                }
            } catch (Throwable throwable) {
                TjRollingFileAppender.LOG.error("Try rollover failed, will retry after 1 minute.", throwable);
            }
        }, 10, 60, TimeUnit.SECONDS);
    }

    public TjRollingFileAppender() {
        APPENDERS.add(this);
    }

    /**
     * 触发Rollover
     */
    private void checkRollover() {
        if (this.isStarted()) {
            synchronized (this.triggeringPolicy) {
                if (this.triggeringPolicy.isTriggeringEvent(this.currentlyActiveFile, (E) null)) {
                    this.rollover();
                }
            }
        }
    }

    /**
     * This method differentiates RollingFileAppender from its super class.
     * @param event
     */
    @Override
    protected void subAppend(E event) {
        // 做一些消息处理
        doMessage(event);
        super.subAppend(event);
    }

    private void doMessage(E event) {
        LoggingEvent loggingEvent = (LoggingEvent) event;
        if (loggingEvent.getLevel().isGreaterOrEqual(Level.ERROR)) {
            System.err.println("event = " + event);
            for (StackTraceElement stackTraceElement : loggingEvent.getCallerData()) {
                System.err.println(stackTraceElement);
            }
        }
    }
}
