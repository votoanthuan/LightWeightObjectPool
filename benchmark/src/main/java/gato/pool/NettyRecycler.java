package gato.pool;

import io.netty.util.Recycler;

public class NettyRecycler {

    private static final Recycler<NettyRecycler> RECYCLER = new Recycler<NettyRecycler>() {
        protected NettyRecycler newObject(Recycler.Handle<NettyRecycler> handle) {
            return new NettyRecycler(handle);
        }
    };

    public static NettyRecycler newInstance() {
        NettyRecycler obj = RECYCLER.get();
        return obj;
    }

    private final Recycler.Handle<NettyRecycler> handle;
    public final StringBuilder sb;

    private NettyRecycler(Recycler.Handle<NettyRecycler> handle) {
        this.handle = handle;
        this.sb = new StringBuilder();
    }

    public void recycle() {
        sb.setLength(0);
        handle.recycle(this);
    }
}