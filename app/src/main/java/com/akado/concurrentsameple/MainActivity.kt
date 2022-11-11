package com.akado.concurrentsameple

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.akado.concurrentsameple.butler.dispatcher.DispatcherLoader
import com.akado.concurrentsameple.test.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.RunnableFuture

class MainActivity : AppCompatActivity() {

    private val testMap = HashMap<String, TestDelegate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testMap.apply {
            put("Promise", PromiseTest())
            put("ShopifyPromise", ShopifyPromiseTest())
            put("Coroutine", CoroutineTest())
            put("Butler", ButlerTest())
        }

        findViewById<Button>(R.id.btn_thread).setOnClickListener {
            testMap["Promise"]?.run()
        }

        findViewById<Button>(R.id.btn_thread_cancel).setOnClickListener {
            testMap["Promise"]?.cancel()
        }

        findViewById<Button>(R.id.btn_promise).setOnClickListener {
            testMap["ShopifyPromise"]?.run()
        }

        findViewById<Button>(R.id.btn_promise_cancel).setOnClickListener {
            testMap["ShopifyPromise"]?.cancel()
        }


        findViewById<Button>(R.id.btn_coroutine).setOnClickListener {
            testMap["Coroutine"]?.run()
        }

        findViewById<Button>(R.id.btn_coroutine_cancel).setOnClickListener {
            testMap["Coroutine"]?.cancel()
        }

        findViewById<Button>(R.id.btn_butler).setOnClickListener {
//            testMap["Butler"]?.run()
            Log.v("ITEST", "start")

            val handler = Handler(Looper.getMainLooper())
            val future = DispatcherLoader.ioDispatcher.submit {
                Log.v("ITEST", " - job start : ${Thread.currentThread().name}")
                for (i in 1..1_000_000_000) {
                }

                handler.post {
                    Log.v("ITEST", " - run start : ${Thread.currentThread().name}")
                    for (i in 1..1_000_000_000) {
                    }
                    Log.v("ITEST", " - run end : ${Thread.currentThread().name}")
                }

                Log.v("ITEST", " - job end : ${Thread.currentThread().name}")
            }
            future.get()
            Log.v("ITEST", "end")
        }

        findViewById<Button>(R.id.btn_butler_cancel).setOnClickListener {
//            testMap["Butler"]?.cancel()
        }
    }
}