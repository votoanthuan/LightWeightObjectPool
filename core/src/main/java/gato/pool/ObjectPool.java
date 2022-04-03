package gato.pool;

import java.util.ArrayDeque;

public abstract class ObjectPool<T> {
    private final ThreadLocal<ArrayDeque<T>> threadLocal;
    private final int maxCapacityPerThread;

    public ObjectPool(int maxCapacityPerThread) {
        this.maxCapacityPerThread = maxCapacityPerThread;
        threadLocal = new ThreadLocal<>();
    }

    /**
     * Create new object
     */
    public abstract T create();

    /**
     * When an object is returned to the pool, the object will be passivated
     */
    protected abstract boolean passivate(T object);

    /**
     * Add object to pool and passivate it
     */
    public boolean recycle(T object) {
        ArrayDeque<T> queue = getQueue();
        if (queue.size() >= maxCapacityPerThread)
            return false;
        if (!passivate(object))
            return false;
        queue.add(object);
        return true;
    }

    /**
     * Get object form pool
     */
    public T get() {
        ArrayDeque<T> queue = getQueue();
        T object = queue.poll();
        if (object == null)
            return create();
        return object;
    }

    private ArrayDeque<T> getQueue() {
        ArrayDeque<T> queue = threadLocal.get();
        if (queue == null) {
            queue = new ArrayDeque<>(maxCapacityPerThread);
            threadLocal.set(queue);
        }
        return queue;
    }

    public int getMaxCapacityPerThread() {
        return maxCapacityPerThread;
    }

    int getQueueSize ()
    {
        return getQueue().size();
    }
}
