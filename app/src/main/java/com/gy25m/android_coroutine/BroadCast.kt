package com.gy25m.android_coroutine

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper

class BroadCast(private val context: Context) : BroadcastReceiver() {
    private val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val handler = Handler(Looper.getMainLooper())

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
            // WiFi 스캔 결과가 사용 가능할 때마다 스캔 재시작
            handler.postDelayed({
                startScan()
            }, 5000) // 5초 후에 스캔 재시작
        }
    }

    private fun startScan() {
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }
        wifiManager.startScan()
    }

    fun register() {
        val filter = IntentFilter()
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(this, filter)
    }

    fun unregister() {
        context.unregisterReceiver(this)
    }
}