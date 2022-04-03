# Lightweight Object Pool
Lightweight object pool based on a thread-local stack.


Benchmark                              Mode  Cnt       Score       Error   Units
BenchmarkObjectPool.apachePool        thrpt    9   54514.347 �  5178.100  ops/ms
BenchmarkObjectPool.lightweightPool   thrpt    9  125481.973 �  2687.455  ops/ms
BenchmarkObjectPool.nettyRecycler     thrpt    9   45152.174 �  2038.958  ops/ms
BenchmarkObjectPool.nettyThreadLocal  thrpt    9  104741.838 � 15473.478  ops/ms
BenchmarkObjectPool.queuePool         thrpt    9    4496.613 �   139.488  ops/ms
