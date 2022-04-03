package gato.pool;

import io.netty.util.concurrent.FastThreadLocal;

import java.util.ArrayDeque;

public class NettyThreadLocal {
    private final FastThreadLocal<ArrayDeque<StringBuilder>> local = new FastThreadLocal<>();

    protected int poolCapacity = 32;

    public StringBuilder get() {
        ArrayDeque<StringBuilder> queue = getQueue();
        StringBuilder sb = queue.poll();
        if (sb == null)
            return new StringBuilder();
        return sb;
    }

    public void add(StringBuilder sb) {
        sb.setLength(0);
        getQueue().add(sb);
    }

    private ArrayDeque<StringBuilder> getQueue() {
        ArrayDeque<StringBuilder> queue = local.get();
        if (queue == null) {
            queue = new ArrayDeque<>(poolCapacity);
            local.set(queue);
        }
        return queue;
    }
}
