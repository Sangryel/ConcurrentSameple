package com.akado.concurrentsameple.test

interface TestDelegate {

    val TAG: String
        get() = "ITEST"

    fun run()
    fun cancel()
}