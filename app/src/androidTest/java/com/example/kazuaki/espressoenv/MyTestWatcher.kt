package com.example.kazuaki.espressoenv

import android.support.test.espresso.Espresso
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.base.DefaultFailureHandler
import java.util.concurrent.atomic.AtomicBoolean


class MyTestWatcher : TestWatcher() {
    override fun failed(e: Throwable, description: Description) {
        val errorHandled: AtomicBoolean = AtomicBoolean(false)

        Espresso.setFailureHandler { error, viewMatcher ->
            MyScreenshot().takeScreenshot(description, error.toString())
            errorHandled.set(true)
            DefaultFailureHandler(InstrumentationRegistry.getTargetContext()).handle(error, viewMatcher)
        }

        super.failed(e, description)
    }
}
