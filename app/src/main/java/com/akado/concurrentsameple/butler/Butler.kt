package com.akado.concurrentsameple.butler

import java.util.concurrent.ExecutorService

object Butler {

    fun <R> create(
        dispatcher: ExecutorService = Dispatchers.IO,
        block: () -> R
    ): ButlerContext<R> =
        ButlerContext(dispatcher, block)

    fun <R> launch(
        dispatcher: ExecutorService = Dispatchers.IO,
        block: () -> R
    ): ButlerContext<R> =
        ButlerContext(dispatcher, block).subscribe()
}