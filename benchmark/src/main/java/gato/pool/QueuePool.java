package gato.pool;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class QueuePool<T> {
    private final ConcurrentLinkedQueue<T> queue;
    protected final int poolCapacity;
    protected final int objectCapacity;

    public QueuePool(int poolInitSize, int poolCapacity, int objectCapacity) {
        int numProcessors = Runtime.getRuntime().availableProcessors();
        if (poolInitSize <= 0)
            poolInitSize = numProcessors * 2;
        if (poolCapacity <= 0)
            poolCapacity = numProcessors * 8;
        this.poolCapacity = Math.max(poolInitSize, poolCapacity);
        this.objectCapacity = objectCapacity;

        queue = new ConcurrentLinkedQueue<>();

//        for (int i = poolInitSize; i > 0; i--)
//            queue.add(newObject());
    }

    public int size() {
        return queue.size();
    }

    public boolean add(T object) {
        if (queue.size() >= poolCapacity)
            return false;

        queue.add(object);
        return true;
    }

    public T get() {
        T value = queue.poll();
        return (value == null) ? newObject() : value;
    }

    protected abstract T newObject();
}
