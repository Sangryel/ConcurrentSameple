package com.akado.concurrentsameple.butler

object Butler {

    fun <R> create(block: () -> R): ButlerContext<R> =
        ButlerContext(block)
}