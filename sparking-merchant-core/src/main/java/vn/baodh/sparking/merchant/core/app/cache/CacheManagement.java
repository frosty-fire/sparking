package vn.baodh.sparking.merchant.core.app.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheManagement {
    private final RedisCache cache;

    public void initQrTokenSession(String qrToken) {
        if (!cache.getClient().getAtomicLong(qrToken).isExists()) {
            cache.getClient().getAtomicLong(qrToken).set(0);
        }
    }

    public long getQrTokenSession(String qrToken) {
        return cache.getClient().getAtomicLong(qrToken).getAndIncrement();
    }

    public boolean deleteQrTokenSession(String qrToken) {
        return cache.getClient().getAtomicLong(qrToken).delete();
    }
}
