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

//fun <T> withContext(
//    dispatcher: Dispatchers,
//    block: () -> T
//) : T
//
//}

fun yield() = Butler.launch(Dispatchers.IO) { }

