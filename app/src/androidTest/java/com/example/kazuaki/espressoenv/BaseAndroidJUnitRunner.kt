package com.example.kazuaki.espressoenv

import android.Manifest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnitRunner
import androidx.test.runner.permission.PermissionRequester
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import java.io.File


class BaseAndroidJUnitRunner : AndroidJUnitRunner() {

    val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE

    // We can use GrantPermissionRule for each test case. But this time, we'd like to allow external storage
    // permission before starting test since we'd like to save screenshot there.
    //
    // We can't grant permission against external storage on android API 28 emulator so far since https://issuetracker.google.com/issues/80393450
    private fun grantPermissions(permissions: Array<String>) {
        val requester = PermissionRequester()
        requester.addPermissions(*permissions)
        requester.requestPermissions()
    }

    private fun enableScreenshotsPermissions() {
        grantPermissions(arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE))
    }

    override fun onStart() {
        enableScreenshotsPermissions()

        clearTestData()

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

    // An example for IdleResource
    // private fun configureIdleResourceEspresso() {
    //     val apiClient = ApiClient(InstrumentationRegistry.getTargetContext().applicationContext)
    //
    //     IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS)
    //     IdlingPolicies.setIdlingResourceTimeout(30, TimeUnit.SECONDS)
    //
    //     val resource = OkHttp3IdlingResource.create("OkHttp", apiClient.okHttpClient)
    //     IdlingRegistry.getInstance().register(resource)
    //     HttpRequestCreator.apiClient = apiClient
    // }

    private fun clearTestData() {
        clearLocalData()
        MyScreenshot().deleteAllScreenshots()
    }

    private fun clearLocalData() {
        val cacheDir = InstrumentationRegistry.getInstrumentation().targetContext.cacheDir
        val appDir = File(cacheDir.parent)
        // For example. appDir.list has the following directories.
        // appDir.list:: cache
        // appDir.list:: code_cache
        // appDir.list:: lib
        // appDir.list:: shared_prefs
        // appDir.list:: no_backup
        // appDir.list:: databases
        // appDir.list:: app_dxmaker_cache
        // appDir.list:: files
        if (appDir.exists()) {
            val fileNames = appDir.list()
            fileNames.forEach {
                if (it != "lib") {
                    deleteFile(File(appDir, it))
                }
            }
        }
    }

    private fun deleteFile(file: File?): Boolean {
        var deletedAll = true

        if (file == null) return deletedAll

        if (file.isDirectory) {
            val children = file.list()
            children.forEachIndexed { index, _ ->
                deletedAll = deleteFile(File(file, children[index])) && deletedAll
            }
        } else {
            deletedAll = file.delete()
        }
        return deletedAll
    }
}
