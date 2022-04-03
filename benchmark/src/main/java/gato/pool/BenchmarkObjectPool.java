package gato.pool;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

public class BenchmarkObjectPool {

    final static PoolStringBuilder queuePool = new PoolStringBuilder(100);
    final static ApachePool apachePool = new ApachePool();
    final static NettyThreadLocal nettyThreadLocal = new NettyThreadLocal();
    final static StringBuilderPool lightweightPool = new StringBuilderPool();


    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                //.include("BenchmarkObjectPool")
                .forks(3)
                .warmupIterations(3)
                .warmupTime(TimeValue.seconds(2))
                .measurementIterations(3)
                .measurementTime(TimeValue.seconds(2))
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @Threads(16)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void lightweightPool() {
        StringBuilder o = lightweightPool.get();
        o.append("xxxxx");
        lightweightPool.recycle(o);
    }

    @Benchmark
    @Threads(16)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void queuePool() {
        StringBuilder o = queuePool.get();
        o.append("xxxxx");
        queuePool.add(o);
    }

    @Benchmark
    @Threads(16)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void nettyRecycler() {
        NettyRecycler o = NettyRecycler.newInstance();
        o.sb.append("xxxxx");
        o.recycle();
    }

    @Benchmark
    @Threads(16)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void apachePool() {
        StringBuilder s = apachePool.create();
        s.append("xxxxx");
        apachePool.wrap(s);
    }

    @Benchmark
    @Threads(16)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void nettyThreadLocal() {
        StringBuilder s = nettyThreadLocal.get();
        s.append("xxxxx");
        nettyThreadLocal.add(s);
    }



}
