package com.akado.concurrentsameple.test

import android.util.Log
import com.akado.concurrentsameple.butler.Butler
import com.akado.concurrentsameple.butler.ButlerContext

class ButlerTest : TestDelegate {

    private var butlerContext: ButlerContext<Int>? = null

    override fun run() {
        Log.v(TAG, "butler start : ${Thread.currentThread().name}")
        val start = System.currentTimeMillis()
        butlerContext = Butler.create {
            Log.v(TAG, "butler : ${Thread.currentThread().name}")
            var out = 0
            for (i in 1..10_000) {
                out += i
                Log.v(TAG, " - process : $i")
            }
            Log.v(TAG, "butler duration: ${(System.currentTimeMillis() - start)}")
        }.subscribe({
            Log.v(TAG, "butler result : ${Thread.currentThread().name}")
        }, {
            Log.v(TAG, "butler fail : ${Thread.currentThread().name}. $it")
        })
    }

    override fun cancel() {
        Log.v(TAG, "butler butlerContext cancel")
        butlerContext?.cancel()
    }
}