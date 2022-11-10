package com.akado.concurrentsameple.butler.dispatcher

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object DispatcherLoader {

    private class MainThreadExecutor : Executor {

        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    val mainDispatcher: Executor = MainThreadExecutor()

    val ioDispatcher: ScheduledExecutorService =
        Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors())
}