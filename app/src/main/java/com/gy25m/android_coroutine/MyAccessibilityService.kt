@file:Suppress("UNREACHABLE_CODE")

package com.gy25m.android_coroutine

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import kotlin.io.path.Path


class MyAccessibilityService : AccessibilityService() {

    // 화면 터치 이벤트 발생 시 호출됨
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "touch") {
            val x = intent.getIntExtra(" x", 0)
            val y = intent.getIntExtra("y", 0)
            clickScreen(x.toFloat(), y.toFloat())
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 화면 클릭
     */
    private fun clickScreen(x: Float, y: Float, startTime: Long = 100, duration: Long = 100) {
        val gestureBuilder = GestureDescription.Builder()
        val path = android.graphics.Path()
        path.moveTo(x, y)
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, startTime, duration))
        val gesture = gestureBuilder.build()
        dispatchGesture(gesture, object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
                super.onCompleted(gestureDescription)
                // 화면 터치 제스처 실행 완료
            }

            override fun onCancelled(gestureDescription: GestureDescription?) {
                super.onCancelled(gestureDescription)
                // 화면 터치 제스처 실행 취소
            }
        }, null)
    }

    // 서비스 중단 시 호출
    override fun onInterrupt() {
    }
}

//private fun isAccessibilityServiceEnabled(context: Context, service: Class<out AccessibilityService>): Boolean {
//    val expectedComponentName = ComponentName(context, service)
//
//    val enabledServicesSetting = Settings.Secure.getString(
//        context.contentResolver,
//        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
//    ) ?: return false
//
//    val colonSplitter = TextUtils.SimpleStringSplitter(':')
//    colonSplitter.setString(enabledServicesSetting)
//
//    while (colonSplitter.hasNext()) {
//        val componentNameString = colonSplitter.next()
//        val enabledComponentName = ComponentName.unflattenFromString(componentNameString)
//        if (enabledComponentName != null && enabledComponentName == expectedComponentName)
//            return true
//    }
//
//    return false
//}