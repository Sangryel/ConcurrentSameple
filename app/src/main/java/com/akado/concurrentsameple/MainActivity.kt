package com.akado.concurrentsameple

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.akado.concurrentsameple.test.*

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
            testMap["Butler"]?.run()
        }

        findViewById<Button>(R.id.btn_butler_cancel).setOnClickListener {
            testMap["Butler"]?.cancel()
        }
    }
}