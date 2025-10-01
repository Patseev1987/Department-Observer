package utils.stubs

import data.FileManager

object FakeFileManager: FileManager {
    override fun saveFileInDownloads(fileName: String, bytes: ByteArray) {
        println("Saving to download folder$fileName")
    }

    override fun saveFileInInternalStorage(fileName: String, bytes: ByteArray) {
        println("Saving to internal folder $fileName")
    }

    override fun isExternalStorageWritable(): Boolean {
        println("isExternalStorageWritable")
        return false
    }
}