package com.akado.concurrentsameple.butler

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class ButlerContext<R>(
    private val dispatcher: ExecutorService,
    private val block: () -> R
) {
    private var future: Future<Unit>? = null

    fun subscribe(
        onSuccess: (R) -> Unit = { },
        onFail: (Throwable) -> Unit = { }
    ): ButlerContext<R> {
        future = dispatcher.submit<Unit> {
            try {
                val result = block.invoke()
                onSuccess.invoke(result)
            } catch (throwable: Throwable) {
                onFail.invoke(throwable)
            }
        }

        dispatcher.execute { future?.get() }

        return this
    }

    fun cancel() {
        future?.cancel(true)
    }
}