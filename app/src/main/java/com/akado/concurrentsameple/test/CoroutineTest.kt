package com.akado.concurrentsameple.test

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoroutineTest : TestDelegate {

    override fun run() {
        Log.v(TAG, "coroutine start : ${Thread.currentThread().name}")
        val start = System.currentTimeMillis()
        GlobalScope.launch(Dispatchers.IO) {
            Log.v(TAG, "coroutine : ${Thread.currentThread().name}")
            var out = 0
            for (i in 1..1_000_000_000) {
                out += i
            }
            Log.v(TAG, "coroutine duration: ${(System.currentTimeMillis() - start)}")
        }
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }
}