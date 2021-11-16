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

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Crawler {

    private Scheduler scheduler;
    private Downloader downloader;
    private Processor processor;
    private Saver saver;

    private int threadNum = 5;
    private ThreadPoolExecutor poolExecutor;

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

    public Crawler addSeed(String url) {
        scheduler.offer(url);
        return this;
    }

    public Crawler addRule(String rule) {
        processor.addRule(rule);
        return this;
    }

    public void run() {

        while (true) {
            String seed = scheduler.poll();
            if (seed == null && poolExecutor.getActiveCount() == 0) {
                poolExecutor.shutdown();
                break;
            } else if (seed == null) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                poolExecutor.execute(() -> {
                    Page currPage = downloader.download(seed);
                    processor.process(currPage);
                    currPage.getNextSeeds().forEach(url -> scheduler.offer(url));
                    saver.save(currPage);
                });
            }

            // todo: 用锁使在下一次循环前已有 seed 进入到 scheduler 中，否则会导致主线程提前结束
            // 此处暂时用延时模拟并发情况下的锁
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
