package com.example.kazuaki.espressoenv

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleRobolectricTest {
    @Test
    fun target_context() {
        val contextPackageName = InstrumentationRegistry.getInstrumentation().targetContext.packageName
        assertThat(contextPackageName).isEqualTo("com.example.kazuaki.espressoenv")
    }

    @Test
    fun context() {
        val contextPackageName = InstrumentationRegistry.getInstrumentation().context.packageName
        assertThat(contextPackageName).isEqualTo("com.example.kazuaki.espressoenv")
        assertThat(contextPackageName).isNotEqualTo("com.example.kazuaki.espressoenv.test")
    }
}
