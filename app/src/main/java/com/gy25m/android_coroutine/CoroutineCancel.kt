package com.gy25m.android_coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.FileInputStream
import kotlin.coroutines.cancellation.CancellationException

class CoroutineCancel {
    fun main(): Unit = runBlocking {
        var parentJob = launch {
            println("[parent] started")
            launch {
                println("[child] started")
                for (i in 0..10) {
                    delay(100)
                    println("i:$i")
                }
                println("[child] complete")
            }
            delay(1000)
            println("[parent] complete")
        }
    }


    fun invokeOnCompletion(): Unit = runBlocking {
        val stream = FileInputStream("file")
        val job = launch(Dispatchers.IO) {
            println("started")
        }
        println("completed")
        job.invokeOnCompletion {
            if (it is CancellationException) {
                println("cancelled")
            }
            stream.close()
        }
        delay(500)
        job.cancel()
    }

}