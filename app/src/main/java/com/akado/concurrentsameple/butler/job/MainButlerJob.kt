package com.akado.concurrentsameple.butler.job

import com.akado.concurrentsameple.butler.Job
import java.util.concurrent.Executor

class MainButlerJob(
    private val dispatcher: Executor,
    private val block: () -> Unit
) : Job {

    override fun start() {
        dispatcher.execute { block.invoke() }
    }

    override fun cancel() {

    }
}