package com.example.kazuaki.espressoenv

import android.app.Instrumentation
import android.graphics.Bitmap
import android.os.Environment
import android.support.test.InstrumentationRegistry
import org.junit.runner.Description
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MyScreenshot {
    private val png: String = ".png"
    private val underscore: String = "_"
    private val screenshotPath: File = File(Environment.getExternalStorageDirectory(), "screenshots")

    fun deleteAllScreenshots() {
        try {
            val screenshots: Array<out File> = screenshotPath.listFiles() ?: return
            screenshots.forEach { it.delete() }
        } catch (ignored: Exception) {

        }
    }

    fun takeScreenshot(description: Description, text: String) {
        takeScreenshot(description.className, description.methodName, text)
    }

    fun takeScreenshot(className: String, methodName: String, description: String) {
        if (android.os.Build.VERSION.SDK_INT < 18) { return }

        val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
        val screenshot: Bitmap = instrumentation.uiAutomation.takeScreenshot()

        val buffer: BufferedOutputStream? = null

        screenshotPath.mkdirs()
        Chmod().plusRWX(screenshotPath)

        try {
            "$className$underscore$methodName$underscore$description$png".let {
                BufferedOutputStream(FileOutputStream(File(screenshotPath, it))).let {
                    screenshot.compress(Bitmap.CompressFormat.PNG, 90, it)
                    it.flush()
                }
            }
        } catch (ignored: IOException) {

        } finally {
            try {
                buffer?.close()
            } catch (ignored: IOException) {

            }

            screenshot.recycle()
        }
    }
}