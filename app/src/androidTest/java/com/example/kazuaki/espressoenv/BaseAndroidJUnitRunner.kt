package com.example.kazuaki.espressoenv

import android.Manifest
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnitRunner
import com.linkedin.android.testbutler.TestButler
import android.support.test.runner.permission.PermissionRequester


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

        enableScreenshotsPermissions()

        MyScreenshot().deleteAllScreenshots()

        super.onStart()
    }
}
