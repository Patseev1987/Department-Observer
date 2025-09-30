package data

interface FileManager {

    fun saveFileInDownloads(fileName: String, bytes: ByteArray)

    fun saveFileInInternalStorage(fileName: String, bytes: ByteArray)

    fun isExternalStorageWritable(): Boolean

}