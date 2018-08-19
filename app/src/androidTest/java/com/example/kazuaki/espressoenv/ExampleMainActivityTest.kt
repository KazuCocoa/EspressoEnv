package com.example.kazuaki.espressoenv

import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleMainActivityTest {

    private val myTestWatcher: MyTestWatcher = MyTestWatcher()
    private val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @get:Rule val ruleChain = RuleChain.outerRule(myTestWatcher).around(activityRule)

    private val espressoTestIdlingResource = CountingIdlingResource("my_activity")

    private class CountStateListenerImpl(val idlingResource: CountingIdlingResource) : MainActivity.CountStateListener {
        override fun onCountStateChanged() {
            idlingResource.dumpStateToLogs() // Idle: count: 0 and has never been busy!
            idlingResource.increment()
            idlingResource.dumpStateToLogs() // Busy: 1 and was last busy at: 3465529 AND NEVER WENT IDLE!
        }

        override fun backToZero() {
            idlingResource.dumpStateToLogs() // Busy: 1 and was last busy at: 3465529 AND NEVER WENT IDLE!
            idlingResource.decrement()
            idlingResource.dumpStateToLogs() // Idle: count: 0 and was last busy at: 3465529 and last went idle at: 3465529
        }

    }

    @Before
    fun before() {
        activityRule.activity.countState = CountStateListenerImpl(espressoTestIdlingResource)
        IdlingRegistry.getInstance().register(espressoTestIdlingResource)
    }

    @Test
    fun test() {
        MainPageObject
            .tapFabButton()
            .tapFabButton() // Next perform doesn't start during the resource is idle.
            .assertFabButton()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(espressoTestIdlingResource)
    }

}

// PageObject using `apply` to enable method chain
object MainPageObject {
    fun tapFabButton() = apply {
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())
    }

    fun assertFabButton() = apply {
        Espresso.onView(ViewMatchers.withId(R.id.fab)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}