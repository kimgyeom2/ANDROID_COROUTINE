package com.gy25m.android_coroutine

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.net.UnknownHostException

open class BaseViewModel(application: Application) : ViewModel(){


//    val useComplexWork = MutableLiveData(false)
//    val sendSignal = MutableLiveData<Boolean>(false)
//    var idError= MutableLiveData<String>()
//
//    init {
//        useComplexWork.value = Preferences.useComplexWork && UserHolder.isSub()
//    }
//
//    val useCase = UseCase()
//
//    protected val compositeDisposable = CompositeDisposable()
//    val showProgress = MutableLiveData<ViewEvent<Boolean>>()
//
//    val showScrollDialog = MutableLiveData<ViewEvent<Pair<String,String>>>()
//
//    protected fun showProgress() {
//        showProgress.postValue(ViewEvent(true))
//    }
//
//    protected fun hideProgress() {
//        showProgress.postValue(ViewEvent(false))
//    }
//
//
//    //region 중복 알림음 쿨타임
//    var beforeData = ""
//    var beforeTime = 0L
//    private fun bfrDupCheck(data: String): Boolean = (data == beforeData).apply {
//        if(!this){
//            beforeData = data
//            beforeTime = System.currentTimeMillis()
//        }else{
//            val nowTime = System.currentTimeMillis()
//            if(nowTime - beforeTime > 3000){
//                toast(R.string.toast_duplication_number)
//                Reaction.playBeepFailed()
//                beforeTime = nowTime
//            }
//        }
//    }
//    //endregion
//
//    protected fun saveData(data: InvoiceEntity){
//        if(bfrDupCheck(data.keyToDupCheck)) return
//
//        if(complexOffc.value?.length != 4 && NowCate.isComplexWork() && Preferences.useComplexWork){
//            toast("집배코드를 입력해주세요")
//            return
//        }
//
//        useCase.saveData(data)
//            .doOnError { errToast(it) }
//            .doOnComplete {
//                if(NowCate.isComplexWork() && Preferences.useComplexWork) saveComplexData(data.barcode)
//                Reaction.playBeepSucceed()
//                Reaction.vibrate()
//            }
//            .autoDispose(this)
//            .subscribe()
//    }
//
//    /**
//     *  복합작업 저장
//     */
//    val complexOffc = MutableLiveData(try{UserHolder.getUser().offc}catch (e: Exception){""})
//    private fun saveComplexData(barcode: String){
//        if (barcode.type() !in listOf(Params.BARCODE_CGO, Params.BARCODE_WBL)) return
//
//        val entity = InvoiceEntity(
//            cate = if(NowCate.isReceivingWork()) Cate.RECEIVING.code else Cate.DELIVERY_TAKING_OVER.code,
//            offc = complexOffc.value ?: getUser().offc
//        ).setBarcode(barcode)
//
//        useCase.saveData(entity, if(NowCate.isReceivingWork()) Cate.RECEIVING.code else Cate.DELIVERY_TAKING_OVER.code)
//            .doOnError { toast((if(NowCate.isReceivingWork()) "입고에러 :" else "배송에러 :") + (it.message ?: "null")) }
//            .autoDispose(this)
//            .subscribe()
//    }
//
//    private var sending = false
//    fun sendInvoices(){
//        if(Preferences.lockTouch) {
//            toast(R.string.toast_locked_touch_msg)
//            return
//        }
//        if(sending) return
//
//        useCase.sendUnsentInvoices()
//            .doOnSubscribe {
//                sending = true
//                showProgress()
//            }
//            .doOnSuccess { successHandling(it) }
//            .doOnError { errorHandling(it) }
//            .doFinally {
//                sending = false
//                sendSignal.postValue(true)
//                hideProgress()
//            }
//            .autoDispose(this)
//            .subscribe()
//    }
//
//    /**
//     *  전송오류내용 대화창으로 보여줌
//     *  오류 없을시 성공 토스트 출력
//     */
//    fun successHandling(response: List<SendDataResponse>){
//
//        val reqError = response
//            .filter { it.status != "OK" }
//            .map { "전송에러(${it.status})\n ${it.message}\n ${it.errorMessage}" }
//            .distinct()
//
//        val dataError = response
//            .map { it.data }
//            .flatten()
//            .filter { it.status != "200" }
//            .map { "에러(${it.key.parseKey()})\n ${it.message}" }
//
//        if(reqError.isEmpty() && dataError.isEmpty()) toast(R.string.toast_sent)
//        else {
//            val errorText = reqError.joinToString(separator = "\n") + "\n" +dataError.joinToString(separator = "\n")
//            showScrollDialog.postValue(ViewEvent(Pair("전송오류",errorText)))
//            errorLoging(errorText)
//        }
//    }
//
//
//    fun errorHandling(error: Throwable){
//        if(error is UnknownHostException) {
//            toast(R.string.toast_err_internet_connection)
//            return
//        }
//
//        // 아이디가 올바르지않을 경우, 비번틀렸을 경우와는 error.'code'가 다름
//        if (error.message=="9416"){
//            idError.postValue(error.message)
//            return
//        }
//
//        when(error.message){
//            "HTTP 400 " -> R.string.toast_bad_req_err_msg.idToString()
//            "HTTP 401 " -> R.string.toast_pda_time_err_msg.idToString()
//            "HTTP 429 " -> R.string.toast_over_call_limit_err_msg.idToString()
//            "HTTP 999 " -> R.string.toast_etc_err_msg.idToString()
//            else -> (error.message ?: "null 오류")
//        }.let {
//            toast(it)
//        }
//    }
//
//    /**
//     *  전송성공 데이터오류 발생시 파이어베이스 로그 업로드
//     */
//    @SuppressLint("HardwareIds")
//    fun errorLoging(errText: String){
//
//        val logFilePath = "SendErrorLog/${Utils.getDateTime("yyyy_MM_dd")}/sendError_${getUser().name}_${Build.SERIAL}.txt"
//
//        val logText = "appVer.${Version().getDevVersion()}\n" +
//                "serial: ${Build.SERIAL}\n" +
//                "userInfo: ${getUser()} \n\n" +
//                "errText\n" +
//                "$errText \n\n" +
//                "errEntity\n"
//
//        useCase.getUnsentInvoices()
//            .doOnSuccess {
//                Firebase.fileLog(
//                    logText + it.joinToString("\n"),
//                    logFilePath
//                )
//            }
//            .doOnError {
//                Firebase.fileLog(
//                    logText + "$it",
//                    logFilePath
//                )
//            }
//            .autoDispose(this)
//            .subscribe()
//
//        Firebase.errorLog(Exception("전송오류_id_${getUser().id}"))
//    }
//
//    /**
//     * Invoice
//     */
//    protected fun isCorrectInvoiceNumber(invoiceNumber: String): Boolean {
//        return if (invoiceNumber.length == 12 || invoiceNumber.length == 10) {
//            // Checksum
//            try {
//                val subNumber = invoiceNumber.substring(0, invoiceNumber.length - 1)
//                val keyNumber =
//                    invoiceNumber.substring(invoiceNumber.length - 1, invoiceNumber.length)
//
//                return keyNumber == (subNumber.toLong() % 7).toString()
//            } catch (e: Exception) {
//                e.printStackTrace()
//                false
//            }
//        } else invoiceNumber.length == 14 || invoiceNumber.length == 13
//    }
//
//    @SuppressLint("SetTextI18n")
//    fun switchComplexWork(view: View){
//        if(Preferences.lockTouch) {
//            toast(R.string.toast_locked_touch_msg)
//            return
//        }
//
//        Preferences.useComplexWork = !Preferences.useComplexWork
//        (view as Button).text = ( if(NowCate.isReceivingWork()) R.string.auto_receving.idToString() else R.string.auto_delivery.idToString() ) +
//                ( if(Preferences.useComplexWork) "ON" else "OFF" )
//        toast((if(NowCate.isReceivingWork()) "자동입고 " else "자동배송") + (if(Preferences.useComplexWork) "사용" else "중지"))
//        useComplexWork.value = Preferences.useComplexWork
//    }
//
//    /**
//     *  타이틀 길게 눌러 터치잠금
//     */
//    val lockTouch = MutableLiveData(Preferences.lockTouch)
//
//    fun lockTouch(){
//        Preferences.lockTouch = !Preferences.lockTouch
//        lockTouch.value = Preferences.lockTouch
//
//        if(Preferences.lockTouch) toast(R.string.toast_lock_touch)
//        Reaction.vibrate()
//    }
//
//    override fun onCleared() {
//        compositeDisposable.dispose()
//        super.onCleared()
//    }
}
