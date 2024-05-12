package com.imprarce.android.frencheducation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.io.File
import java.io.FileOutputStream

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
//        val assetManager = applicationContext.assets
//        val databaseDir = "/data/data/${packageName}/databases"
//        val databasePath = "$databaseDir/frenchDb.db"
//
//        if (!File(databasePath).exists()) {
//            File(databaseDir).mkdirs()
//            val inputStream = assetManager.open("frenchDb.db")
//            val outputStream = FileOutputStream(databasePath)
//            val buffer = ByteArray(1024)
//            var length: Int
//            while (inputStream.read(buffer).also { length = it } > 0) {
//                outputStream.write(buffer, 0, length)
//            }
//            outputStream.flush()
//            outputStream.close()
//            inputStream.close()
//        }
    }
}