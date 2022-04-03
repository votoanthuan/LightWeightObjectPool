package gato.pool;

public class StringBuilderPool extends ObjectPool<StringBuilder> {
    private final int stringCapacity;

    public StringBuilderPool() {
        this(16);
    }

    public StringBuilderPool(int stringCapacity) {
        this(stringCapacity, 16);
    }

    public StringBuilderPool(int stringCapacity, int maxCapacityPerThread) {
        super(maxCapacityPerThread);
        this.stringCapacity = stringCapacity;
    }

    @Override
    public StringBuilder create() {
        return new StringBuilder(stringCapacity);
    }

    @Override
    protected boolean passivate(StringBuilder object) {
        object.setLength(0);
        return true;
    }
}
