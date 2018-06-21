package com.example.kazuaki.espressoenv

import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MyTestWatcher : TestWatcher() {
    override fun failed(e: Throwable, description: Description) {
        MyScreenshot().takeScreenshot(description, e.toString())
        super.failed(e, description)
    }
}
