package com.akado.concurrentsameple.butler.job

import com.akado.concurrentsameple.butler.Dispatchers
import com.akado.concurrentsameple.butler.Job
import com.akado.concurrentsameple.butler.dispatcher.DispatcherLoader

internal fun buildJob(dispatchers: Dispatchers, block: () -> Unit) : Job =
    when(dispatchers) {
        Dispatchers.IO -> IOButlerJob(block)
        Dispatchers.Main -> MainButlerJob(block)
    }