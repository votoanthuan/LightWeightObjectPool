package gato.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringBuilderPoolTest {
    @Test
    void simple() {
        StringBuilderPool pool = new StringBuilderPool();
        assertEquals(0, pool.getQueueSize());

        StringBuilder sb = pool.get();
        assertEquals(0, sb.length());
        assertEquals(0, pool.getQueueSize());

        sb.append("Test");
        assertEquals(4, sb.length());

        pool.recycle(sb);
        assertEquals(0, sb.length());
        assertEquals(1, pool.getQueueSize());

        StringBuilder sb1 = pool.get();
        assertEquals(sb, sb1);
        assertEquals(0, sb1.length());
        assertEquals(0, pool.getQueueSize());
    }

    @Test
    void getQueueSize() {
        int maxSize = 32;
        StringBuilderPool pool = new StringBuilderPool(16, maxSize);

        for (int i = maxSize * 4; i >= 0; i--) {
            StringBuilder sb = new StringBuilder();
            pool.recycle(sb);
            assertTrue(pool.getQueueSize() > 0);
            assertTrue(pool.getQueueSize() <= maxSize);
        }

        for (int i = maxSize * 4; i >= 0; i--) {
            StringBuilder sb = pool.get();
            assertEquals(0, sb.length());
            assertTrue(pool.getQueueSize() <= maxSize);
        }

    }

    static StringBuilderPool pool = new StringBuilderPool();

    @Test
    void multiThread() throws Exception {
        int numberOfThreads = 64;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        final int maxSize = 32;

        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                for (int j = maxSize * 8; j >= 0; j--) {
                    StringBuilder sb = pool.get();
                    assertEquals(0, sb.length());
                    sb.append("Test").append(ThreadLocalRandom.current().nextLong());
                    pool.recycle(sb);
                    assertTrue(pool.getQueueSize() <= maxSize);
                }
                latch.countDown();
            });
        }
        latch.await();
        assertTrue(pool.getQueueSize() <= maxSize);
    }
}