package com.example.kazuaki.espressoenv

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    val classNam: String = this.javaClass.name

    @get:Rule
    val myTestWatcher: MyTestWatcher = MyTestWatcher()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        MyScreenshot().takeScreenshot(classNam, "useAppContext", "neko")
        assertThat(appContext.packageName).isEqualTo("com.example.kazuaki.espressoenv")
        assertWithMessage("message").that(appContext.packageName).isEqualTo("com.example.kazuaki.espressoenv")
    }
}
