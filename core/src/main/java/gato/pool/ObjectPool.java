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
     * Creates an instance that can be served by the pool
     *
     * @return an instance
     */
    protected abstract T create();

    /**
     * When an instance is returned to the pool, the instance will be passivated
     *
     * @param object the instance will be passivated
     * @return the result of the passivation process. If true, the object will be added to the pool
     */
    protected abstract boolean passivate(T object);

    /**
     * Add object to pool and passivate it
     *
     * @param object the instance will be added to pool
     * @return the result of the recycle process
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
     * Get an instance form the pool
     *
     * @return an instance
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
