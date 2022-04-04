# Lightweight Object Pool
Lightweight object pool based on a thread-local stack.

Benchmark compare Lightweight Object Pool (version 1.0.0) with Apache Common Pool 2 (version 2.11.1) and Netty Object Pool (version 4.1.75)
<pre>
Benchmark                              Mode  Cnt       Score      Error   Units
BenchmarkObjectPool.lightweightPool   thrpt    9  198159.406   4810.279  ops/ms
BenchmarkObjectPool.apachePool        thrpt    9   76567.949   1226.223  ops/ms
BenchmarkObjectPool.nettyRecycler     thrpt    9  127063.701   8922.951  ops/ms
</pre>