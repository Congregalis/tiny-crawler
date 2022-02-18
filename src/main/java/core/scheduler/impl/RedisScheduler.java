package core.scheduler.impl;

import core.model.Seed;
import core.scheduler.Scheduler;
import core.util.RedissonUtil;
import org.redisson.api.RQueue;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

public class RedisScheduler implements Scheduler {

    private static final String QUEUE_NAME = "tiny-crawler-seed-queue";
    private static final String SET_NAME = "tiny-crawler-seed-set";

    private RedissonClient redisClient;
    private RQueue<Seed> redisQueue;
    private RSet<Seed> redisSet;

    public RedisScheduler() {
        redisClient = RedissonUtil.getRedisson();
        redisQueue = redisClient.getQueue(QUEUE_NAME);
        redisSet = redisClient.getSet(SET_NAME);
    }

    public RedisScheduler(String address, int port, String password) {
        redisClient = RedissonUtil.getRedisson(address, port, password);
        redisQueue = redisClient.getQueue(QUEUE_NAME);
        redisSet = redisClient.getSet(SET_NAME);
    }

    @Override
    public void offer(Seed seed) {
        if (!redisSet.contains(seed)) {
            redisQueue.offer(seed);
        }
    }

    @Override
    public Seed poll() {
        return redisQueue.poll();
    }

    public void shutdown() {
        redisClient.shutdown();
    }
}
