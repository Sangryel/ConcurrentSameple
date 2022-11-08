package com.akado.concurrentsameple.test

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.shopify.promises.Promise
import com.shopify.promises.startOn
import com.shopify.promises.then
import java.util.concurrent.Executors

class ShopifyPromiseTest : TestDelegate {

    private var promise: Promise<Int, Throwable>? = null

    override fun run() {
        Log.v(TAG, "Shopify Promise(${Thread.currentThread().name}). start")
        val start = System.currentTimeMillis()
        val handler = Handler(Looper.myLooper()!!)
        promise = Promise.ofSuccess<Unit, Throwable>(Unit)
            .startOn(Executors.newSingleThreadExecutor())
            .then {
                Promise {
                    onCancel {
                        Log.v(TAG, "Shopify Promise(${Thread.currentThread().name}). onCancel")
                    }

                    Log.v(TAG, "Shopify Promise(${Thread.currentThread().name})")
                    var out = 0
                    for (i in 1..1_000_000_000) {
                        out += i
                        if (i % 100_000_000 == 0) {
                            Thread.sleep(100L)
                            Log.v(TAG, " - process : $i")
                        }
                    }
                    Log.v(
                        TAG,
                        "Shopify Promise(${Thread.currentThread().name}). duration: ${(System.currentTimeMillis() - start)}"
                    )
                    resolve(out)
                }
            }
            .whenComplete {
                handler.post {
                    when (it) {
                        is Promise.Result.Success ->
                            Log.v(
                                TAG,
                                "Shopify Promise(${Thread.currentThread().name}). result : ${it.value}"
                            )
                        is Promise.Result.Error ->
                            Log.v(
                                TAG,
                                "Shopify Promise(${Thread.currentThread().name}). error : $it"
                            )
                    }
                }
            }
        Log.v(TAG, "Shopify Promise(${Thread.currentThread().name}). setOnClickListener end")
    }

    override fun cancel() {
        Log.v(TAG, "Shopify Promise(${Thread.currentThread().name}). cancel")
        promise?.cancel()
    }
}