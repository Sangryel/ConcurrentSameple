package com.akado.concurrentsameple.test

import android.util.Log
import com.akado.concurrentsameple.promise.Promise

class PromiseTest : TestDelegate {

    private var promise: Promise<Int>? = null

    override fun run() {
        Log.v(TAG, "Promise(${Thread.currentThread().name}). start")
        val start = System.currentTimeMillis()
        promise = Promise.withPromise(block = {
            Promise(block = { resolve, reject ->
                Log.v(TAG, "Promise(${Thread.currentThread().name})")
                var out = 0
                for (i in 1..1_000_000_000) {
                    out += i

                    if (i % 100_000_000 == 0) {
//                        Thread.sleep(100L)
                        Log.v(TAG, " - progress : $i")
                    }
                }
                Log.v(
                    TAG,
                    "Promise(${Thread.currentThread().name}). duration: ${(System.currentTimeMillis() - start)}"
                )
                resolve(out)
            })
        })
    }

    override fun cancel() {
        Log.v(TAG, "Promise(${Thread.currentThread().name}). cancel")
        promise?.reject(Throwable())
    }
}