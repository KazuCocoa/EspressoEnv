package com.example.kazuaki.espressoenv

import android.preference.PreferenceManager
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MyTestWatcher : TestWatcher() {
    override fun starting(description: Description?) {
        clearData() // Clear shared preference data in setUp phase
        super.starting(description)
    }

    override fun failed(e: Throwable, description: Description) {
        MyScreenshot().takeScreenshot(description, e.toString())
        super.failed(e, description)
    }

    private fun clearData() {
        val preferenceManager = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getInstrumentation().targetContext)
        preferenceManager.edit().clear().commit()

        InstrumentationRegistry.getInstrumentation().targetContext.cacheDir.deleteRecursively()
        // Need GrantPermission
        InstrumentationRegistry.getInstrumentation().targetContext.externalCacheDirs.forEach { it.deleteRecursively() }
    }
}
