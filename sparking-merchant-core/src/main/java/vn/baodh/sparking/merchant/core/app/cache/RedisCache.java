package vn.baodh.sparking.merchant.core.app.cache;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisCache {
    private final RedissonClient client;

    RedisCache() {
        client = Redisson.create();
        log.info(client.getId());
    }

    public RedissonClient getClient() {
        return this.client;
    }
}
