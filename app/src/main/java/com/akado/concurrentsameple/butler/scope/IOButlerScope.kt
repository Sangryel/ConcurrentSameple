package com.akado.concurrentsameple.butler.scope

import com.akado.concurrentsameple.butler.Scope
import java.util.concurrent.ScheduledExecutorService

class IOButlerScope<T>(
    private val dispatcher: ScheduledExecutorService,
    private val block: () -> T
) : Scope<T> {

//    private var future: Future<Unit>? = null
//
//    override fun start() {
//        Thread.sleep(10L)
//        future = dispatcher.submit<Unit> { block.invoke() }
//        dispatcher.execute { future?.get() }
//    }

    override fun get(): T {
        TODO("Not yet implemented")
    }

}