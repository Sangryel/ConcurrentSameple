package com.akado.concurrentsameple.butler.job

import com.akado.concurrentsameple.butler.Job
import com.akado.concurrentsameple.butler.dispatcher.DispatcherLoader
import java.util.concurrent.Executor

internal class MainButlerJob(
    private val block: () -> Unit
) : Job {

    override fun start() {
        DispatcherLoader.mainDispatcher.execute { block.invoke() }
    }

    override fun cancel() {

    }
}