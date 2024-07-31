# Demo

This repo demonstrates that Redis Cache `@CacheResult` doesn't run in worker threads.

```
quarkus dev
```

It will start a graphql service, the graphiql interface is accessible at
[http://localhost:8080/q/graphql-ui/].


Send a query
```graphql
query {
  hello(name: "name_a") {
    id
    name
  }
}
```

The code will hang, and after 1 minute, it will time with and error like this
```
WARN  [io.ver.cor.imp.BlockedThreadChecker] (vertx-blocked-thread-checker) Thread Thread[vert.x-worker-thread-3,5,main] has been blocked for 60427 ms, time limit is 60000 ms: io.vertx.core.VertxException: Thread blocked
	at java.base/jdk.internal.misc.Unsafe.park(Native Method)
	at java.base/java.util.concurrent.locks.LockSupport.park(LockSupport.java:221)
	at java.base/java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:754)
	at java.base/java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1099)
	at java.base/java.util.concurrent.CountDownLatch.await(CountDownLatch.java:230)
	at io.smallrye.mutiny.operators.uni.UniBlockingAwait.await(UniBlockingAwait.java:67)
	at io.smallrye.mutiny.groups.UniAwait.atMost(UniAwait.java:65)
	at io.smallrye.mutiny.groups.UniAwait.indefinitely(UniAwait.java:46)
	at io.quarkus.cache.runtime.CacheResultInterceptor.intercept(CacheResultInterceptor.java:110)
	at io.quarkus.cache.runtime.CacheResultInterceptor_Bean.intercept(Unknown Source)
	at io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:42)
	at io.quarkus.arc.impl.AroundInvokeInvocationContext.perform(AroundInvokeInvocationContext.java:30)
	at io.quarkus.arc.impl.InvocationContexts.performAroundInvoke(InvocationContexts.java:27)
	at org.acme.Service_Subclass.dbCall(Unknown Source)
	at org.acme.Service.hello(Service.java:12)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at io.smallrye.graphql.execution.datafetcher.helper.ReflectionInvoker.invoke(ReflectionInvoker.java:97)
	at io.quarkus.smallrye.graphql.runtime.spi.datafetcher.QuarkusDefaultDataFetcher.lambda$invokeAndTransformBlocking$0(QuarkusDefaultDataFetcher.java:84)
	at io.smallrye.context.impl.wrappers.SlowContextualCallable.call(SlowContextualCallable.java:21)
	at io.vertx.core.impl.ContextImpl.lambda$executeBlocking$0(ContextImpl.java:178)
	at io.vertx.core.impl.ContextInternal.dispatch(ContextInternal.java:279)
	at io.vertx.core.impl.ContextImpl.lambda$internalExecuteBlocking$2(ContextImpl.java:210)
	at io.vertx.core.impl.TaskQueue.run(TaskQueue.java:76)
	at org.jboss.threads.ContextHandler$1.runWith(ContextHandler.java:18)
	at org.jboss.threads.EnhancedQueueExecutor$Task.doRunWith(EnhancedQueueExecutor.java:2516)
	at org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2495)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1521)
	at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:11)
	at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:11)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:1583)
```