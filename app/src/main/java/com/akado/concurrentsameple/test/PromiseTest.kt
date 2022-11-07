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
                for (i in 1..50_000) {
                    out += i
                    Log.v(TAG, " - process : $i")
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