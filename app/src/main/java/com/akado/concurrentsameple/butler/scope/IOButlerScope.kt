package com.akado.concurrentsameple.butler.scope

import android.os.Looper
import com.akado.concurrentsameple.butler.Scope
import com.akado.concurrentsameple.butler.dispatcher.DispatcherLoader
import java.util.concurrent.ScheduledExecutorService

internal class IOButlerScope<T>(
    private val block: () -> T
) : Scope<T> {

    override fun get(): T =
        if (Looper.myLooper() == Looper.getMainLooper()) {
            DispatcherLoader.ioDispatcher.submit<T> { block.invoke() }.get()
        } else {
            block.invoke()
        }
}