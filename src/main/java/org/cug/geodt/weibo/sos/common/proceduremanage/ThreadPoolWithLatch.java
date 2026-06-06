package org.cug.geodt.weibo.sos.common.proceduremanage;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Supplier;


/**
 * 多线程工具类
 */
public class ThreadPoolWithLatch {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolWithLatch.class);
    private static final long DEFAULT_TIMEOUT = 3;
    private static final TimeUnit DEFAULT_TIMEOUT_UNIT = TimeUnit.MINUTES;
    /**
     * 计数器
     */
    private CountDownLatch latch;
    /**
     * 线程池
     */
    private CaughtExceptionLatchExecutor pool;
    private final long timeout;
    private final TimeUnit timeoutUnit;
    private final ThreadFactory namedThreadFactory;

    public ThreadPoolWithLatch(int latchCount) {
        this(latchCount, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT_UNIT);
    }

    public ThreadPoolWithLatch(int latchCount, String threadFactoryName) {
        this(latchCount, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT_UNIT, threadFactoryName);
    }

    public ThreadPoolWithLatch(int latchCount, long timeout, TimeUnit timeoutUnit) {
        this(latchCount, timeout, timeoutUnit, ThreadPoolWithLatch.class.getSimpleName() + "[" + UUID.randomUUID() + "]");
    }

    public ThreadPoolWithLatch(int latchCount, long timeout, TimeUnit timeoutUnit, String threadFactoryName) {
        this(timeout, timeoutUnit, threadFactoryName);
        this.initLatchAndThreadPool(latchCount);
    }

    public ThreadPoolWithLatch() {
        this(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT_UNIT);
    }

    public ThreadPoolWithLatch(String threadFactoryName) {
        this(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT_UNIT, threadFactoryName);
    }

    public ThreadPoolWithLatch(long timeout, TimeUnit timeoutUnit) {
        this(timeout, timeoutUnit, ThreadPoolWithLatch.class.getSimpleName() + "[" + UUID.randomUUID() + "]");
    }

    public ThreadPoolWithLatch(long timeout, TimeUnit timeoutUnit, String threadFactoryName) {
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
        this.namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("TheadPool[" + threadFactoryName + "]").build();
    }

    private void initLatchAndThreadPool(int latchCount) {
        this.latch = new CountDownLatch(latchCount);
        this.pool = new CaughtExceptionLatchExecutor(latch, namedThreadFactory);
    }

    /**
     * 停止：清零计数器，停止线程池
     */
    public void stop() {
        pool.shutdown();
        if (Objects.isNull(latch)) {
            return;
        }
        while (latch.getCount() > 0) {
            latch.countDown();
        }
    }

    /**
     * 执行线程任务
     */
    public void execute(Supplier<List<Runnable>> runnableCollection) throws RuntimeException {
        try {
            this.executeWithoutInterrupted(runnableCollection);
        } catch (InterruptedException e) {
            logger.warn("Interrupted by external thread.");
        } finally {
            stop();
        }
    }

    /**
     * 执行线程任务（不捕获中断异常）
     */
    public void executeWithoutInterrupted(Supplier<List<Runnable>> runnableCollection) throws RuntimeException, InterruptedException {
        List<Runnable> runnableList = runnableCollection.get();
        if (Objects.isNull(latch)) {
            initLatchAndThreadPool(runnableList.size());
        }
        if (runnableList.size() != latch.getCount()) {
            stop();
            throw new IllegalArgumentException("The number of threads to be executed does not match the definition.");
        }
        runnableList.forEach(pool::execute);
        if (!latch.await(timeout, timeoutUnit)) {
            logger.warn("The waiting time elapsed " + timeout + StringUtils.SPACE + timeoutUnit + " before the count reached zero, " + latch.getCount() + " threads were forced to stop.");
            stop();
        }
        for (Throwable throwable : pool.getThrowableList()) {
            // 如有异常，抛出第一个
            throw new RuntimeException(throwable);
        }
    }

    /**
     * 捕获异常的线程池
     */
    private static class CaughtExceptionLatchExecutor extends ThreadPoolExecutor {

        /**
         * 线程池各线程产生的异常
         */
        private final List<Throwable> throwableList = new LinkedList<>();
        private final CountDownLatch latch;

        public CaughtExceptionLatchExecutor(CountDownLatch latch, ThreadFactory threadFactory) {
            this(latch, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory);
        }

        public CaughtExceptionLatchExecutor(CountDownLatch latch,
                                            long keepAliveTime,
                                            TimeUnit unit,
                                            BlockingQueue<Runnable> workQueue,
                                            ThreadFactory threadFactory) {
            this(latch, (int) latch.getCount(), (int) latch.getCount(), keepAliveTime, unit, workQueue, threadFactory);
        }

        public CaughtExceptionLatchExecutor(CountDownLatch latch,
                                            int corePoolSize,
                                            int maximumPoolSize,
                                            long keepAliveTime,
                                            TimeUnit unit,
                                            BlockingQueue<Runnable> workQueue,
                                            ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
            this.latch = latch;
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            // 线程执行完成进入本步骤
            if (Objects.nonNull(t)) {
                // 如有异常则记录
                throwableList.add(t);
            }
            // 计数器减1
            latch.countDown();
        }

        public List<Throwable> getThrowableList() {
            return throwableList;
        }
    }

}
