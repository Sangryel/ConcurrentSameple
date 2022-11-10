package com.akado.concurrentsameple.butler

interface Job {

    fun start()

    fun cancel()
}