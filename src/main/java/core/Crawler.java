package core;

import core.downloader.Downloader;
import core.downloader.impl.HttpClientDownloader;
import core.model.Page;
import core.model.Seed;
import core.processor.Processor;
import core.processor.impl.SimpleTextProcessor;
import core.saver.Saver;
import core.saver.impl.ConsoleSaver;
import core.scheduler.Scheduler;
import core.scheduler.impl.QueueScheduler;
import core.scheduler.impl.RedisScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Crawler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Scheduler scheduler;
    private Downloader downloader;
    private Processor processor;
    private Saver saver;

    private int threadNum = 5;
    private int exitSleepTime = 10;
    private int maxDepth = 20;
    private long maxPages = Long.MAX_VALUE;

    private ThreadPoolExecutor poolExecutor;
    private AtomicLong pageCount;
    private final Lock newSeedLock = new ReentrantLock();
    private final Condition newSeedCondition = newSeedLock.newCondition();

    private Crawler() {
        init();
    }

    public static Crawler build() {
        return new Crawler();
    }

    private void init() {
        thread(threadNum);
        pageCount = new AtomicLong(0);

        if (scheduler == null) scheduler = new QueueScheduler();
        if (downloader == null) downloader = new HttpClientDownloader();
        if (processor == null) processor = new SimpleTextProcessor();
        if (saver == null) saver = new ConsoleSaver();
    }

    public Crawler maxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
        return this;
    }

    public Crawler maxPages(int maxPages) {
        this.maxPages = maxPages;
        return this;
    }

    public Crawler maxPages(long maxPages) {
        this.maxPages = maxPages;
        return this;
    }

    public Crawler thread(int num) {
        return thread(num, 1500L);
    }

    public Crawler thread(int num, long keepAliveTime) {
        threadNum = num;

        poolExecutor = new ThreadPoolExecutor(threadNum, threadNum, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        return this;
    }

    public Crawler setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public Crawler setDownloader(Downloader downloader) {
        this.downloader = downloader;
        return this;
    }

    public Crawler setProcessor(Processor processor) {
        this.processor = processor;
        return this;
    }

    public Crawler setSaver(Saver saver) {
        this.saver = saver;
        return this;
    }

    public Crawler setExitSleepTime(int time) {
        exitSleepTime = time;
        return this;
    }

    public Crawler addSeed(String url) {
        scheduler.offer(new Seed(url));
        return this;
    }

    public Crawler addRule(String rule) {
        processor.addRule(rule);
        return this;
    }

    /**
     * ????????????
     */
    public void shutdown() {
        // ???????????????
        poolExecutor.shutdown();

        // ????????? Redis ??????????????????????????? Redis ?????????
        if (scheduler instanceof RedisScheduler) {
            ((RedisScheduler) scheduler).shutdown();
        }

        // Saver ????????????
        saver.shutdown();
    }

    /**
     * ???????????? seed ???????????????????????????????????????
     */
    public boolean waitNewSeed() {
        long res = 0;
        newSeedLock.lock();
        try {
//            res = newSeedCondition.await(exitSleepTime, TimeUnit.MILLISECONDS);
            res = newSeedCondition.awaitNanos(TimeUnit.SECONDS.toNanos(exitSleepTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            newSeedLock.unlock();
        }

        return res <= 0;
    }

    /**
     * ???????????????????????? seed??????????????????
     */
    public void signalNewSeed() {
        newSeedLock.lock();
        try {
            newSeedCondition.signal();
        } finally {
            newSeedLock.unlock();
        }

    }

    public void run() {
        logger.info("???????????????");

        while (true) {
            if (poolExecutor.getActiveCount() + poolExecutor.getCompletedTaskCount() + poolExecutor.getQueue().size() >= maxPages && !poolExecutor.isShutdown()) {
                logger.info("????????????????????????????????? {}?????????????????????????????????...", maxPages);
                poolExecutor.shutdown();

                while (scheduler.poll() != null);
            }
            Seed seed = scheduler.poll();
            if ((seed == null || poolExecutor.isShutdown()) && poolExecutor.getActiveCount() == 0) {
                if (!waitNewSeed()) {
                    logger.info("??????????????? " + pageCount.get() + " ?????????");
                    logger.debug("????????? {} ????????????????????????????????? {} ??????????????????????????? {} ????????????", poolExecutor.getActiveCount(), poolExecutor.getCompletedTaskCount(), poolExecutor.getQueue().size());
                    continue;
                }

                logger.info("{} ????????????????????????seed???????????????????????????????????????????????????????????? {} ????????????????????????...", exitSleepTime, pageCount.get());
                shutdown();
                break;
            } else if (seed == null || poolExecutor.isShutdown()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                poolExecutor.execute(() -> {
                    logger.debug("?????????????????????{}", seed.toString());
                    Page currPage = new Page(seed, downloader.download(seed.getUrl()));
                    processor.process(currPage);
                    currPage.getNextSeeds().forEach(next -> {
                        if (next.getDepth() <= maxDepth) scheduler.offer(next);
                    });
                    signalNewSeed();
                    saver.save(currPage);
                    pageCount.getAndIncrement();
                });
            }
        }
    }
}
