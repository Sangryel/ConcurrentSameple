package com.akado.concurrentsameple.test

import android.util.Log
import com.akado.concurrentsameple.butler.*

class ButlerTest : TestDelegate {

    private var job: Job? = null

    override fun run() {
        Log.v(TAG, "butler start(${Thread.currentThread().name})")
        val start = System.currentTimeMillis()
        job = Butler.launch(Dispatchers.IO) {
            Log.v(TAG, "butler run(${Thread.currentThread().name})")
            val out = job()
            Log.v(
                TAG,
                "butler duration(${Thread.currentThread().name}) : ${(System.currentTimeMillis() - start)}"
            )
            output(out)
        }
    }

    override fun cancel() {
        Log.v(TAG, "butler butlerContext cancel")
        job?.cancel()
    }

    private fun job() : Int = withContext {
        var out = 0
        Log.v(TAG, " - butler job start(${Thread.currentThread().name}")
        for (i in 1..1_000_000_000) {
            out += 1

            if (i % 100_000_000 == 0) {
                yield()
            }

//            if(Thread.interrupted()) {
//                Log.v(TAG, " - butler job interrupted")
//                throw InterruptedException()
//            }
        }
        Log.v(TAG, " - butler job end(${Thread.currentThread().name}")
        out
    }

    private fun output(output: Int) = Butler.launch(Dispatchers.Main) {
        Log.v(TAG, "butler output(${Thread.currentThread().name}) : $output")
    }
}