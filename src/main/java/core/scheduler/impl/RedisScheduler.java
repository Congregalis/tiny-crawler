package core.scheduler.impl;

import core.model.Seed;
import core.scheduler.Scheduler;
import core.util.RedissonUtil;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RQueue;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

public class RedisScheduler implements Scheduler {

    private static final String QUEUE_NAME = "tiny-crawler-seed-queue";
    private static final String SET_NAME = "tiny-crawler-seed-set";

    private RedissonClient redisClient;
    private RQueue<Seed> redisQueue;
    private RBloomFilter<Seed> redisBloomFilter;

    public RedisScheduler() {
        redisClient = RedissonUtil.getRedisson();
        initComponent();
    }

    public RedisScheduler(String address, int port, String password) {
        redisClient = RedissonUtil.getRedisson(address, port, password);
        initComponent();
    }

    public void initComponent() {
        redisQueue = redisClient.getQueue(QUEUE_NAME);
        redisBloomFilter = redisClient.getBloomFilter(SET_NAME);
        redisBloomFilter.tryInit(55000000L, 0.03);
    }

    @Override
    public void offer(Seed seed) {
        if (!redisBloomFilter.contains(seed)) {
            redisQueue.offer(seed);
            redisBloomFilter.add(seed);
        }
    }

    @Override
    public Seed poll() {
        return redisQueue.poll();
    }

    public void shutdown() {
        redisClient.getKeys().deleteByPattern("*tiny-crawler*");
        redisClient.shutdown();
    }
}
