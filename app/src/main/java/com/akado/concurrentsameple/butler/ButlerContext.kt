package com.akado.concurrentsameple.butler

import java.util.concurrent.Future
import java.util.concurrent.FutureTask

class ButlerContext<R>(
    private val block: () -> R
) {

    private var future: Future<Unit>? = null
//    private var onSuccess: (R) -> Unit = { }
//    private var onFail: (Throwable) -> Unit = { }
//
//    fun onSuccess(onSuccess: (R) -> Unit) {
//        this.onSuccess = onSuccess
//    }
//
//    fun onFail(onFail: (Throwable) -> Unit) {
//        this.onFail = onFail
//    }

    fun subscribe(
        onSuccess: (R) -> Unit,
        onFail: (Throwable) -> Unit = { }
    ): ButlerContext<R> {
        future = ButlerResources.EXECUTOR.submit<Unit> {
            try {
                val result = block.invoke()
                onSuccess.invoke(result)
            } catch (e: InterruptedException) {
                onFail.invoke(e)
                Thread.currentThread().interrupt()
            } catch (throwable: Throwable) {
                onFail.invoke(throwable)
            }
        }

        ButlerResources.EXECUTOR.execute { future?.get() }

        return this
    }

    fun cancel() {
        future?.let {
            it.cancel(true)
        }
    }


}