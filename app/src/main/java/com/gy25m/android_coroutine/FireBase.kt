package com.gy25m.android_coroutine

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(private val logsDirectory: File)
    //: FileRepository
    {
//    override fun createLogFile(
//        terminalUserCode: String,
//        agencyUserCode: String,
//        screenName: String,
//        content: String
//    ) {
//        try {
//            if (!logsDirectory.exists()) {
//                logsDirectory.mkdirs()
//            }
//
//            val file = File(logsDirectory, getFileName(terminalUserCode, agencyUserCode))
//            val contentHeader =
//                "【ERROR】[${getCurrentDate() + " " + getCurrentTime()}][${screenName}]"
//
//            if (!file.exists()) {
//                file.createNewFile()
//            }
//
//            BufferedWriter(FileWriter(file, true)).use { buf ->
//                buf.append("$contentHeader $content")
//                buf.newLine()
//            }
//        } catch (e: Exception) {
//            throw e
//        }
//    }
//
//    override fun getFile(terminalUserCode: String, agencyUserCode: String): File? =
//        logsDirectory.listFiles()?.find {
//            it.name == getFileName(terminalUserCode, agencyUserCode)
//        }
//
//    private fun getFileName(terminalUserCode: String, agencyUserCode: String): String {
//        val fileNameStringBuilder = StringBuilder()
//        if (terminalUserCode != "") fileNameStringBuilder.append("t${terminalUserCode}_")
//        if (agencyUserCode != "") fileNameStringBuilder.append("a${agencyUserCode}_")
//        return fileNameStringBuilder.append("${getCurrentDate()}.txt").toString()
//    }
}