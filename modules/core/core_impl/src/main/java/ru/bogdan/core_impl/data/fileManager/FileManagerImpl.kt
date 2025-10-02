package ru.bogdan.core_impl.data.fileManager

import android.content.Context
import android.os.Environment
import data.FileManager
import java.io.File
import javax.inject.Inject


class FileManagerImpl @Inject constructor(private val context: Context) : FileManager {
    override fun saveFileInDownloads(fileName: String, bytes: ByteArray) {
        if (!isExternalStorageWritable()) {
            return
        }

        try {
            val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadDir.exists()) {
                downloadDir.mkdirs()
            }
            val file = File(downloadDir, fileName)
            if (file.exists()) {
                file.delete()
            }
            file.writeBytes(bytes)
        } catch (e: Exception) {
            throw RuntimeException("File wasn't been saved")
        }
    }

    override fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}