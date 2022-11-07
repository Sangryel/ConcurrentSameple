package com.akado.concurrentsameple.promise

import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class Promise<Value> {
    internal val lock: Lock
    internal var state: State<Value>

    internal val executor: ScheduledExecutorService
    internal val subscribers: ArrayList<Subscriber<Value>>

    internal constructor(executor: ScheduledExecutorService = Setting.defaultExecutor) {
        this.lock = ReentrantLock()
        this.state = State.Pending()

        this.executor = executor
        this.subscribers = arrayListOf()
    }

    constructor(
        executor: ScheduledExecutorService = Setting.defaultExecutor,
        block: (
            resolve: (Value) -> Unit,
            reject: (Throwable) -> Unit,
        ) -> Unit,
    ): this(executor) {
        executor.execute {
            try {
                block({ resolve(it) }, { reject(it) })
            }
            catch (throwable: Throwable) {
                reject(throwable)
            }
        }
    }

    constructor(
        executor: ScheduledExecutorService = Setting.defaultExecutor,
        block: () -> Value,
    ): this(executor) {
        executor.execute {
            try {
                val value = block()
                resolve(value)
            }
            catch (throwable: Throwable) {
                reject(throwable)
            }
        }
    }

    internal fun resolve(value: Value) {
        lock.lock()
        if (state is State.Pending) {
            state = State.Resolved(value)
            subscribers.forEach { it.onResolved(value) }
        }
        lock.unlock()
    }

    internal fun reject(throwable: Throwable) {
        lock.lock()
        if (state is State.Pending) {
            state = State.Rejected(throwable)
            subscribers.forEach { it.onRejected(throwable) }
        }
        lock.unlock()
    }

    internal fun subscribe(subscriber: Subscriber<Value>) {
        lock.lock()
        val current = state
        when (current) {
            is State.Pending -> subscribers.add(subscriber)
            is State.Resolved -> subscriber.onResolved(current.value)
            is State.Rejected -> subscriber.onRejected(current.throwable)
        }
        lock.unlock()
    }

    companion object {
        fun <Value> withPromise(
            executor: ScheduledExecutorService = Setting.defaultExecutor,
            block: () -> Promise<Value>,
        ): Promise<Value> {
            val promise = Promise<Value>()

            executor.execute {
                try {
                    block().subscribe(Subscriber(
                        executor,
                        { promise.resolve(it) },
                        { promise.reject(it) }
                    ))
                }
                catch (throwable: Throwable) {
                    promise.reject(throwable)
                }
            }

            return promise
        }

        fun <Value> pending(
            executor: ScheduledExecutorService = Setting.defaultExecutor
        ): Triple<Promise<Value>, (Value) -> Unit, (Throwable) -> Unit> {
            val promise = Promise<Value>(executor)

            return Triple(promise, { promise.resolve(it) }, { promise.reject(it) })
        }

        fun <Value> resolved(
            value: Value,
        ): Promise<Value> {
            val promise = Promise<Value>()
            promise.resolve(value)

            return promise
        }

        fun <Value> rejected(
            throwable: Throwable,
        ): Promise<Value> {
            val promise = Promise<Value>()
            promise.reject(throwable)

            return promise
        }
    }

    internal class Subscriber<Value> {
        val onResolved: (Value) -> Unit
        val onRejected: (Throwable) -> Unit

        constructor(
            executor: ScheduledExecutorService,
            onResolved: (Value) -> Unit,
            onRejected: (Throwable) -> Unit,
        ) {
            this.onResolved = { value ->
                executor.execute { onResolved(value) }
            }
            this.onRejected = { throwable ->
                executor.execute { onRejected(throwable) }
            }
        }
    }

    internal sealed class State<Value> {
        class Pending<Value>: State<Value>()
        data class Resolved<Value>(val value: Value): State<Value>()
        data class Rejected<Value>(val throwable: Throwable): State<Value>()
    }

    object Setting {
        var defaultExecutor =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors())
    }

    class TimeoutException: Exception()
}