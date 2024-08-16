package com.gy25m.android_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
fun main() = runBlocking {
    launch {
        wait1000ms()
        println("HELLO !" )
    }
    println("World")
}

suspend fun wait1000ms(){
    delay(1000L)
}
