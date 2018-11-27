package com.example.kazuaki.espressoenv

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import androidx.test.runner.screenshot.Screenshot
import org.junit.runner.Description
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

// Some methods related screenshots and test weatchers, inspired by https://github.com/square/spoon

class MyScreenshot {
    private val png: String = ".png"
    private val underscore: String = "_"
    private val screenshotPath: File = File(Environment.getExternalStorageDirectory(), "app_spoon-screenshots")

    fun deleteAllScreenshots() {
        synchronized(this) {
            val deleteRecursively = screenshotPath.deleteRecursively()
            val result = if (deleteRecursively) "success" else "failure"
            Log.i(MyScreenshot::class.java.name,
                "clearing screenshots from folder ${screenshotPath.absolutePath} $result")
        }
    }

    fun takeScreenshot(description: Description, text: String) {
        takeScreenshot(description.className, description.methodName, text)
    }

    fun takeScreenshot(className: String, methodName: String, description: String) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) { return }

        // The `Screenshot.capture()` wraps `InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();` so far.
        val screenshot: Bitmap = Screenshot.capture().bitmap

        val buffer: BufferedOutputStream? = null

        screenshotPath.mkdirs()
        Chmod().plusRWX(screenshotPath)

        try {
            val screenShotQuality = 90
            "$className$underscore$methodName$underscore$description$png".let {
                BufferedOutputStream(FileOutputStream(File(screenshotPath, it))).let {
                    screenshot.compress(Bitmap.CompressFormat.PNG, screenShotQuality, it)
                    it.flush()
                }
            }
        } catch (ignored: IOException) {
            // empty
        } finally {
            try {
                buffer?.close()
            } catch (ignored: IOException) {
                // empty
            }

            screenshot.recycle()
        }
    }
}
