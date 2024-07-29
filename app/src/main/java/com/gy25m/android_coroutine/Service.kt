package com.gy25m.android_coroutine

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper

class ScanRestartService : Service() {

    private lateinit var wifiManager: WifiManager
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startScan()
        return START_STICKY
    }

    private fun startScan() {
        handler.postDelayed({
            if (!wifiManager.isWifiEnabled) {
                wifiManager.isWifiEnabled = true
            }
            wifiManager.startScan()
            startScan()
        }, 5000) // 5초마다 스캔 재시작
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}