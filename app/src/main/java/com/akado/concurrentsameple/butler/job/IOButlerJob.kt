package com.akado.concurrentsameple.butler.job

import com.akado.concurrentsameple.butler.Job
import java.util.concurrent.Future
import java.util.concurrent.ScheduledExecutorService

class IOButlerJob(
    private val dispatcher: ScheduledExecutorService,
    private val block: () -> Unit
) : Job {

    private var future: Future<Unit>? = null

    override fun start() {
        Thread.sleep(10L)
        future = dispatcher.submit<Unit> { block.invoke() }
        dispatcher.execute { future?.get() }
    }

    override fun cancel() {
        future?.cancel(true)
    }
}