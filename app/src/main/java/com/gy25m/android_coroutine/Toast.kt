package com.gy25m.android_coroutine

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes

/**
 *  토스트 기능강화
 *
 *  호출 간소화
 *  Toast.makeText(application, msg, duration) -> toast(msg, duration(생략가능))
 *
 *  연속호출 처리
 *  호출시 마다 토스트 쌓임(토스트 겹쳐보임, 늦게호출한 토스트 짧은 시간만 보임, 다량호출시 앱 크래시) -> 호출시 기존 토스트 중지
 *
 *  메인스레드 외 호출
 *  메인스레드 외 호출시 에러 -> 내부적으로 쓰레드 에러 처리
 */

val application = try {
    Class.forName("android.app.ActivityThread").getMethod("currentApplication")
        .invoke(null) as Application
} catch (e: Exception) {
    null
}

var toast: Toast? = try {
    Toast.makeText(application, "", Toast.LENGTH_SHORT)
} catch (e: Exception) {
    null
}


fun toast(@StringRes msgResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    toastMakeText(msgResId, duration)
}

fun toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    toastMakeText(msg, duration)
}

private fun <T> toastMakeText(msg: T, duration: Int) {
    //메인 스레드에서 호출시
    if (Looper.myLooper() == Looper.getMainLooper()) {
        toast?.cancel()
        if (msg is String) toast = Toast.makeText(application, msg, duration)
        else if (msg is Int) toast = Toast.makeText(application, msg, duration)
        toast?.show()
    } else {
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                toast = Toast.makeText(application, "", duration)
                toastMakeText(msg, duration)
            }, 0
        )
    }

}