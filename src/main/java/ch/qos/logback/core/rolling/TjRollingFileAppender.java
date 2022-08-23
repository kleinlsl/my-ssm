package ch.qos.logback.core.rolling;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
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
        if (this.isStarted()) {
            processMessage(event);
        }
        super.subAppend(event);
    }

    public void processMessage(E event) {

    }

    /**
     * 获取日志信息
     * @param event
     * @return
     */
    protected String getLogMsg(E event) {
        if (this.encoder instanceof LayoutWrappingEncoder) {
            return ((LayoutWrappingEncoder<E>) this.encoder).getLayout().doLayout(event);
        }
        return null;
    }
}
