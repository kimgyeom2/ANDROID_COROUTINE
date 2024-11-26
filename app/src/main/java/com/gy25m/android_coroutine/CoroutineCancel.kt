package com.gy25m.android_coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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

    fun aa(){
        Thread(Runnable {
            for(i in 1..10) {
                Thread.sleep(1000L)
                print("I'm working in Thread.")
            }
        }).start()
        GlobalScope.launch() {
            repeat(10) {
                delay(1000L)
                print("I'm working in Coroutine.")
            }
        }

        print("Start Main Thread")
        GlobalScope.launch {
            delay(3000)
            print("in Coroutine ...")
        }
        print("End Main Thread")

        GlobalScope.launch {
            launch {
                print("At Here!")
            }
            val value: Int = async {
                var total = 0
                for (i in 1..10) total += i
                total
            }.await()
            print("$value")
        }

//        V: At Here!
//        V: 55

        print("Start...")
        GlobalScope.launch(Dispatchers.Main) {
            val job1 = async(Dispatchers.IO) {
                var total = 0
                for (i in 1..10) {
                    total += i
                    delay(100)
                }
                print("job1")
                total
            }
            val job2 = async(Dispatchers.Main) {
                var total = 0
                for (i in 1..10) {
                    delay(100)
                    total += i
                }
                print("job2")
                total
            }
            val result1 = job1.await()
            val result2 = job2.await()
            print("results are $result1 and $result2")
        }
        print("End.")

        GlobalScope.launch(Dispatchers.IO) {
            val v = withContext(Dispatchers.Main) {
                var total = 0
                for (i in 1..10) {
                    delay(100)
                    total += i
                }
                total
            }
            print("result: $v")
            print("Do something in IO thread")
        }

        GlobalScope.launch {
            val x = doSomething()
            print("done something, $x")
        }
    }
    private suspend fun doSomething():Int {
        val value: Int = GlobalScope.async(Dispatchers.IO) {
            var total = 0
            for (i in 1..10) total += i
            print("do something in a suspend method: $total")
            total
        }.await()
        return value
    }


}