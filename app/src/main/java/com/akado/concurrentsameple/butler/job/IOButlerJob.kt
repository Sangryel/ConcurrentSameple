package com.akado.concurrentsameple.butler.job

import com.akado.concurrentsameple.butler.Job
import com.akado.concurrentsameple.butler.dispatcher.DispatcherLoader
import java.util.concurrent.Future
import java.util.concurrent.ScheduledExecutorService

internal class IOButlerJob(
    private val block: () -> Unit
) : Job {

    private var future: Future<Unit>? = null

    override fun start() {
        future = DispatcherLoader.ioDispatcher.submit<Unit> { block.invoke() }
        DispatcherLoader.ioDispatcher.execute { future?.get() }
    }

    override fun cancel() {
        future?.cancel(true)
    }
}