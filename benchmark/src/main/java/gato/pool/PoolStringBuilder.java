package gato.pool;

public class PoolStringBuilder extends QueuePool<StringBuilder> {
    public PoolStringBuilder(int bufferCapacity) {
        super(0, 0, bufferCapacity);
    }

    public PoolStringBuilder(int poolInitSize, int poolCapacity, int builderCapacity) {
        super(poolInitSize, poolCapacity, builderCapacity);
    }

    @Override
    protected StringBuilder newObject() {
        return new StringBuilder(objectCapacity);
    }

    @Override
    public boolean add(StringBuilder object) {
        object.setLength(0);
        return super.add(object);
    }
}
