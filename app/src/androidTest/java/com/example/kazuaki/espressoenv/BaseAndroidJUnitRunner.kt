package com.example.kazuaki.espressoenv

import android.Manifest
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnitRunner
import com.linkedin.android.testbutler.TestButler
import android.support.v4.content.ContextCompat

import android.content.pm.PackageManager.PERMISSION_GRANTED



class BaseAndroidJUnitRunner : AndroidJUnitRunner() {

    val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE

    private fun permissionGranted(permission: String): Boolean {
        val checkPermission = ContextCompat.checkSelfPermission(targetContext, permission)
        return checkPermission == PERMISSION_GRANTED
    }

    private fun grantPermission(permission: String) {
        if (permissionGranted(permission)) return

        TestButler.grantPermission(InstrumentationRegistry.getTargetContext(), permission)

        if (!permissionGranted(permission)) {
            throw RuntimeException("Failed to grant " + permission)
        }
    }

    private fun enableScreenshots() {
        grantPermission(WRITE_EXTERNAL_STORAGE)
        grantPermission(READ_EXTERNAL_STORAGE)
    }

    override fun onStart() {
        TestButler.setup(InstrumentationRegistry.getTargetContext())

        MyScreenshot().deleteAllScreenshots()

        enableScreenshots()

        super.onStart()
    }
}