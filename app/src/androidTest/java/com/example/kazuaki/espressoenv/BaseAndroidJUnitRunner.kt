package com.example.kazuaki.espressoenv

import android.Manifest
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnitRunner
import com.linkedin.android.testbutler.TestButler
import android.support.test.runner.permission.PermissionRequester
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until


class BaseAndroidJUnitRunner : AndroidJUnitRunner() {

    val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE

    private fun grantPermissions(permissions: Array<String>) {
        val requester = PermissionRequester()
        requester.addPermissions(*permissions)
        requester.requestPermissions()
    }

    private fun enableScreenshotsPermissions() {
        grantPermissions(arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE))
    }

    override fun onStart() {
        TestButler.setup(InstrumentationRegistry.getTargetContext())

//        enableScreenshotsPermissions()

        MyScreenshot().deleteAllScreenshots()

        // For emulators
        closeAppIfGoogleAppShowsARN()

        super.onStart()
    }

    // https://android.googlesource.com/platform/frameworks/base/+/android-cts-7.0_r22/core/res/res/layout/app_error_dialog.xml
    // We can see aerr_mute and aerr_close over 7.0. Not sure under 6.0 though.
    private fun closeAppIfGoogleAppShowsARN() {
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        uiDevice.findObject(By.res("android:id/aerr_close"))?.let {
            it.click()
            uiDevice.wait(Until.gone(By.res("android:id/aerr_close")), 500)
        }
        uiDevice.findObject(By.res("android:id/aerr_mute"))?.let {
            it.click()
            uiDevice.wait(Until.gone(By.res("android:id/aerr_mute")), 500)
        }
    }
}
