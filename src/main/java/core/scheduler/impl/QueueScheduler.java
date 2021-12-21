package core.scheduler.impl;

import core.model.Seed;
import core.scheduler.Scheduler;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueScheduler implements Scheduler {

    private BlockingQueue<Seed> queue = new LinkedBlockingQueue<>();
    private Set<String> set = new HashSet<>();

    @Override
    public void offer(Seed seed) {
        if (set.add(seed.getUrl()))
            queue.offer(seed);
    }

    @Override
    public Seed poll() {
        return queue.poll();
    }
}
