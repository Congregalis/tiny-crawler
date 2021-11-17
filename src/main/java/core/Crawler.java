package core;

import core.downloader.Downloader;
import core.downloader.impl.HttpClientDownloader;
import core.model.Page;
import core.processor.Processor;
import core.processor.impl.SimpleTextProcessor;
import core.saver.Saver;
import core.saver.impl.ConsoleSaver;
import core.scheduler.Scheduler;
import core.scheduler.impl.QueueScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
    private int exitSleepTime = 5000;
    private ThreadPoolExecutor poolExecutor;
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

        if (scheduler == null) scheduler = new QueueScheduler();
        if (downloader == null) downloader = new HttpClientDownloader();
        if (processor == null) processor = new SimpleTextProcessor();
        if (saver == null) saver = new ConsoleSaver();
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
        scheduler.offer(url);
        return this;
    }

    public Crawler addRule(String rule) {
        processor.addRule(rule);
        return this;
    }

    /**
     * 等待新的 seed 加入队列，使主线程阻塞在此
     */
    public void waitNewSeed() {
        newSeedLock.lock();
        try {
            newSeedCondition.await(exitSleepTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            newSeedLock.unlock();
        }
    }

    /**
     * 已取得可能有的新 seed，唤醒主线程
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
        logger.info("爬虫启动！");

        while (true) {
            logger.debug("目前有 {} 个线程正在工作，已完成 {} 个任务，队列中还有 {} 个任务。", poolExecutor.getActiveCount(), poolExecutor.getCompletedTaskCount(), poolExecutor.getQueue().size());
            String seed = scheduler.poll();
            if (seed == null && poolExecutor.getActiveCount() == 0) {
                logger.info("爬虫工作完成，正在退出...");
                poolExecutor.shutdown();
                break;
            } else if (seed == null) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                logger.debug("当前正在爬取：{}", seed);
                poolExecutor.execute(() -> {
                    Page currPage = downloader.download(seed);
                    processor.process(currPage);
                    currPage.getNextSeeds().forEach(url -> scheduler.offer(url));
                    signalNewSeed();
                    saver.save(currPage);
                });
            }

            waitNewSeed();
        }
    }
}
