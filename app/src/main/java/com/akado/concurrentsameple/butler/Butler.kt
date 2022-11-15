package com.akado.concurrentsameple.butler

import com.akado.concurrentsameple.butler.job.buildJob
import com.akado.concurrentsameple.butler.scope.IOButlerScope

object Butler {

    fun launch(
        dispatcher: Dispatchers, block: () -> Unit
    ): Job {
        val job = buildJob(dispatcher, block)
        job.start()
        return job
    }
}

fun yield(): Unit = withContext {
    if (Thread.interrupted()) {
        throw InterruptedException()
    }
}

fun <T> withContext(
    block: () -> T
): T = IOButlerScope(block).get()

