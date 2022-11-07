package com.akado.concurrentsameple.butler

import java.util.concurrent.Executors

object ButlerResources {

    var EXECUTOR =
        Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors())
}