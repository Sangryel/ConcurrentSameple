package com.akado.concurrentsameple.butler

import com.akado.concurrentsameple.butler.job.buildJob

object Butler {

    fun launch(
        dispatcher: Dispatchers,
        block: () -> Unit
    ): Job {
        val job = buildJob(dispatcher, block)
        job.start()
        return job
    }
}

//fun yield() = Thread.sleep(10L)
fun yield() = Butler.launch(Dispatchers.IO) { }

