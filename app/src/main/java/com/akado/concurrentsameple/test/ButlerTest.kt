package com.akado.concurrentsameple.test

import android.util.Log
import com.akado.concurrentsameple.butler.Butler
import com.akado.concurrentsameple.butler.Dispatchers
import com.akado.concurrentsameple.butler.Job
import com.akado.concurrentsameple.butler.yield

class ButlerTest : TestDelegate {

    private var job: Job? = null

    override fun run() {
        Log.v(TAG, "butler start(${Thread.currentThread().name})")
        val start = System.currentTimeMillis()
        job = Butler.launch(Dispatchers.IO) {
            Log.v(TAG, "butler run(${Thread.currentThread().name})")
            var out = 0
            for (i in 1..1_000_000_000) {
                out += i

                if (i % 100_000_000 == 0) {
//                    Thread.sleep(10L)
                    sideJob()
                    Log.v(TAG, " - progress(${Thread.currentThread().name}) : $i")
                }
            }
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

    private fun output(output: Int) = Butler.launch(Dispatchers.Main) {
        Log.v(TAG, "butler output(${Thread.currentThread().name}) : $output}")
    }

    private fun sideJob() = Butler.launch(Dispatchers.IO) {

    }
}