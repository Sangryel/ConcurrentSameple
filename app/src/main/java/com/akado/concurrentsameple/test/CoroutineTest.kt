package com.akado.concurrentsameple.test

import android.util.Log
import kotlinx.coroutines.*

class CoroutineTest : TestDelegate {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.w(TAG, "Unexpected exception emitted in event runner. $throwable")
    }
    private val scope = CoroutineScope(exceptionHandler)

    private var job: Job? = null

    override fun run() {
        Log.v(TAG, "coroutine start : ${Thread.currentThread().name}")
        val start = System.currentTimeMillis()
//        GlobalScope.launch(Dispatchers.Main)
        job = scope.launch {
//            try {
                flush(start)
//            } catch (throwable: Throwable) {
//                Log.w(TAG, "coroutine catch. $throwable")
//            }
        }
    }

    suspend fun flush(start: Long) = withContext(Dispatchers.IO) {
        Log.v(TAG, "coroutine : ${Thread.currentThread().name}")
        var out = 0
        for (i in 1..1_000_000_000) {
            out += i

            if (i % 100_000_000 == 0) {
//                yield()
                trackEvent(i)
            }
        }
        Log.v(TAG, "coroutine duration: ${(System.currentTimeMillis() - start)}")
    }

    suspend fun trackEvent(i: Int) = withContext(Dispatchers.IO) {
        Log.v(TAG, " - trackEvent progress : $i")
    }

    override fun cancel() {
        Log.v(TAG, "coroutine cancel : ${Thread.currentThread().name}")
        job?.cancel()
    }
}