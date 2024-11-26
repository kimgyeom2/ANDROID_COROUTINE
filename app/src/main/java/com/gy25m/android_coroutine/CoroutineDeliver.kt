package com.gy25m.android_coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutineDeliver {
    fun ff() {
        runBlocking {
            print("start")
            val numbers = productNumbers()
            val squares = squares(numbers)
            for (i in 1..5) print("${squares.receive()}")
            print("end")
            coroutineContext.cancelChildren()
        }
    }

    private fun CoroutineScope.productNumbers() = produce<Int> {
        var x = 1
        while (true) {
            print("send ${x} on productNumbers")
            send(x++)
            delay(100)
        }
    }

    private fun CoroutineScope.squares(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> =
        produce {
            for (x in numbers) {
                print("send ${x} on squares")
                send(x * x)
            }
        }

//    V: start
//    V: send 1 on productNumbers
//    V: send 1 on squares
//    V: 1
//    V: send 2 on productNumbers
//    V: send 2 on squares
//    V: 4
//    V: send 3 on productNumbers
//    V: send 3 on squares
//    V: 9
//    V: send 4 on productNumbers
//    V: send 4 on squares
//    V: 16
//    V: send 5 on productNumbers
//    V: send 5 on squares
//    V: 25
//    V: end

    fun gg() {
        runBlocking {
            val producer = productNumbers2()
            repeat(5) {
                launchProcessor(it, producer)
            }
            delay(1000L)
            producer.cancel()
        }
    }

    private fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) {
        launch {
            for (msg in channel) {
                print("Processor #$id received $msg")
            }
        }
    }

    private fun CoroutineScope.productNumbers2() = produce<Int> {
        var x = 1
        while (true) {
            print("send $x on productNumbers")
            send(x++)
            delay(100)
        }
    }
}