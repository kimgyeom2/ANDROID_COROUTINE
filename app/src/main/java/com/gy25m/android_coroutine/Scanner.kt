package com.gy25m.android_coroutine

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

@SuppressLint("StaticFieldLeak")
object Scanner {

    var barcodeIntent = "value"
    private var broadcastAction = "android.intent.action.SCANRESULT"

    private var broadcastReceiver: BroadcastReceiver? = null
    private var mContext: Context? = null
    private val filter = IntentFilter()




    /**
     *   use
     *   = true : 매개 람다식을 브로드캐스트 리시버에 등록
     *   = false : 브로드캐스드 리시버 등록 해제
     */

    fun startScan(use: Boolean = true, onRecv: (String) -> Unit = {}) {

        if (broadcastReceiver != null) {
            mContext?.unregisterReceiver(broadcastReceiver)
            broadcastReceiver = null
        }

        if (use) {
            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent) {
                    val barcode = intent.getStringExtra(
                        barcodeIntent
                    ) ?: ""
                    onRecv(barcode)
                }
            }
            mContext?.registerReceiver(broadcastReceiver, filter)
        }
    }

    /**
     *  use
     *  = true : 스캔버튼 눌러 스캔 가능
     *  = false : 스캔버튼 눌러 스캔 불가능, 연속스캔 중 일시 연속스캔 종료
     */

    fun useScan(use: Boolean = true) {
        if (!use) continceScan(false)
    }


    /**
     *  use
     *  = true : 연속스캔 시작
     *  = flase : 연속스캔 종료
     *  = (매개변수 없음) : 연속스캔 중일시 연속스캔 종료
     *                     연속스캔 종료중일시 연속스캔 시작
     */

    var isContince = false

    fun continceScan(use: Boolean? = null) {
        if (broadcastReceiver != null) {

            isContince = use ?: !isContince

        }
    }


}
