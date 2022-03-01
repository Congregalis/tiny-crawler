package core.util;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonUtil {

    // Redis 服务器IP
    private static String address = "127.0.0.1";
    // Redis 的端口号
    private static int port = 6379;

    public static RedissonClient getRedisson() {
        return getRedisson(address, port);
    }

    public static RedissonClient getRedisson(String address, int port) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + address + ":" + port);

        return Redisson.create(config);
    }

    public static RedissonClient getRedisson(String address, int port, String password) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + address + ":" + port)
                .setPassword(password);

        return Redisson.create(config);
    }
}
