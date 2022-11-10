package com.akado.concurrentsameple.butler

sealed class Dispatchers {

    object IO : Dispatchers()
    object Main : Dispatchers()

}