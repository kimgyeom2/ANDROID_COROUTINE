package com.gy25m.android_coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class CoroutineTimeOut {
    fun bb(){
        print("start")
        val job = GlobalScope.launch {
            withTimeout(4000L) {
                repeat(10) {
                    delay(1000L)
                    print("I'm working.")
                }
            }
        }
        print("end")
    }

//    V: start
//    V: end
//    V: I'm working.
//    V: I'm working.
//    V: I'm working.

    fun cc(){
        runBlocking {
            print("start")
            val channel = Channel<Int>()
            launch {
                for (x in 1..5) {
                    channel.send(x * x)
                }
            }
            repeat(5) {
                val v = channel.receive()
                print("$v")
            }
            print("end")
        }
    }

//    V: start
//    V: 1
//    V: 4
//    V: 9
//    V: 16
//    V: 25
//    V: end


    fun dd(){
        runBlocking {
            print("start")
            val channel = Channel<Int>()
            launch {
                for(x in 1..5) channel.send(x*x)
                channel.close()
            }
            for(y in channel) print("$y")
            print("end")
        }
    }

//    V: start
//    V: 1
//    V: 4
//    V: 9
//    V: 16
//    V: 25
//    V: end

    fun ee(){
        runBlocking {
            print("start")
            val numbers = productNumbers()
            val squares = squares(numbers)
            for(i in 1..5) print("${squares.receive()}")
            print("end")
            coroutineContext.cancelChildren()
        }
    }
    private fun CoroutineScope.productNumbers() = produce<Int> {
        var x = 1
        while(true) {
            print("send ${x} on productNumbers")
            send(x++)
            delay(100)
        }
    }
    private fun CoroutineScope.squares(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        for(x in numbers) {
            print("send ${x} on squares")
            send(x*x)
        }
    }
}