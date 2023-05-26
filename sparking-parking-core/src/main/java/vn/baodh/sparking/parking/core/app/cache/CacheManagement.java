package vn.baodh.sparking.parking.core.app.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheManagement {
    private final RedisCache cache;

    public void initQrTokenSession(String qrToken) {
        cache.getClient().getAtomicLong(qrToken).set(0);
    }

    public long getQrTokenSession(String qrToken) {
        return cache.getClient().getAtomicLong(qrToken).getAndIncrement();
    }

    public boolean deleteQrTokenSession(String qrToken) {
        return cache.getClient().getAtomicLong(qrToken).delete();
    }
}
