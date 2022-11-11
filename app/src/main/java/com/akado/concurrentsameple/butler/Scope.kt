package com.akado.concurrentsameple.butler

interface Scope<T> {

    fun get() : T
}