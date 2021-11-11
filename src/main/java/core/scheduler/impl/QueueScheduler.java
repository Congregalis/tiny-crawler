package core.scheduler.impl;

import core.scheduler.Scheduler;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueScheduler implements Scheduler {

    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private Set<String> set = new HashSet<>();

    @Override
    public void offer(String url) {

        if (set.add(url))
            queue.offer(url);
    }

    @Override
    public String poll() {
        return queue.poll();
    }
}
