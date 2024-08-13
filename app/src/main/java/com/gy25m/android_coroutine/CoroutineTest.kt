package com.gy25m.android_coroutine
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CoroutineTest {

    fun main() = runBlocking {
        var a = async {
            delay(1000)
            return@async
        }
        var b = async {
            delay(500)
            return@async
        }
        println("C")
        println(a.await())
        println(b.await())
        println("D")
    }

    //C, A, B, D 순으로 출력
    @Test
    fun main2() = runBlocking(CoroutineName("CoroutineName #0")) {
        log("main started")
        launch(CoroutineName( "Coroutine $1")) {
            log("Before load")
            val data = loadFromBackground()
            log("data : $data")
        }
        launch(CoroutineName("Coroutine $2")) {
            for (i in 0..5){
                delay(200)
                log("i : $i")
            }
        }
        log("main finished")
    }

    suspend fun log(message: String) {
        println("[${Thread.currentThread()}][${currentCoroutineContext()[CoroutineName]}]$message")
    }

    suspend fun loadFromBackground(): String {
        delay(1000)
        return "Suspend Function"
    }
    //[Thread[main @CoroutineName #0#1,5,main]][CoroutineName(CoroutineName #0)]main started
    //[Thread[main @CoroutineName #0#1,5,main]][CoroutineName(CoroutineName #0)]main finished
    //[Thread[main @Coroutine $1#2,5,main]][CoroutineName(Coroutine $1)]Before load
    //[Thread[main @Coroutine $2#3,5,main]][CoroutineName(Coroutine $2)]i : 0
    //[Thread[main @Coroutine $2#3,5,main]][CoroutineName(Coroutine $2)]i : 1
    //[Thread[main @Coroutine $2#3,5,main]][CoroutineName(Coroutine $2)]i : 2
    //[Thread[main @Coroutine $2#3,5,main]][CoroutineName(Coroutine $2)]i : 3
    //[Thread[main @Coroutine $1#2,5,main]][CoroutineName(Coroutine $1)]data : Suspend Function
    //[Thread[main @Coroutine $2#3,5,main]][CoroutineName(Coroutine $2)]i : 4
    //[Thread[main @Coroutine $2#3,5,main]][CoroutineName(Coroutine $2)]i : 5
}