package com.akado.concurrentsameple.butler.job

import com.akado.concurrentsameple.butler.Dispatchers
import com.akado.concurrentsameple.butler.Job
import com.akado.concurrentsameple.butler.dispatcher.DispatcherLoader

fun buildJob(dispatchers: Dispatchers, block: () -> Unit) : Job =
    when(dispatchers) {
        Dispatchers.IO -> IOButlerJob(DispatcherLoader.ioDispatcher, block)
        Dispatchers.Main -> MainButlerJob(DispatcherLoader.mainDispatcher, block)
    }