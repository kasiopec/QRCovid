package com.kasiopec.qrcovid.tools

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.util.Log
import java.io.*
import java.util.zip.ZipFile

class FileManager constructor(private val context: Context) {
    companion object {
        private const val FILE_NAME = "covidpass"
    }

    fun copyFileToInternal(uri: Uri) {
        try {
            context.contentResolver.openInputStream(uri)?.let { inputStream ->
                context.openFileOutput("$FILE_NAME.zip", MODE_PRIVATE).use { outStream ->
                    try{
                        inputStream.copyTo(outStream)
                    }catch (e: IOException){
                        Log.e("FileManger", "Failed to copy file: $FILE_NAME", e)
                    }
                }
            }
        } catch (e: IOException) {
            Log.e("FileManger", "Failed to save file: $FILE_NAME", e)
        }
    }

    fun unzipCovidPass(){
        val file = File("${context.filesDir}/$FILE_NAME.zip")
        file.unzip()
    }

    private fun File.unzip(to: File? = null) {
        val destinationDir = to ?: File(parentFile, nameWithoutExtension)
        destinationDir.mkdirs()

        ZipFile(this).use { zipFile ->
            zipFile
                .entries()
                .asSequence()
                .filter { !it.isDirectory }
                .forEach { zipEntry ->
                    val currFile = File(destinationDir, zipEntry.name)
                    currFile.parentFile?.mkdirs()
                    zipFile.getInputStream(zipEntry).use { input ->
                        currFile.outputStream().use { output -> input.copyTo(output) }
                    }
                }
        }
    }
}