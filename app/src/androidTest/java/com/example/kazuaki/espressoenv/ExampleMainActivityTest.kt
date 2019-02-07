package com.example.kazuaki.espressoenv

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleMainActivityTest {

    private val myTestWatcher: MyTestWatcher = MyTestWatcher()
    private val scenarioRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @get:Rule val ruleChain: RuleChain = RuleChain.outerRule(myTestWatcher).around(scenarioRule)

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
        scenarioRule.scenario.onActivity { activity ->
            activity.countState = CountStateListenerImpl(espressoTestIdlingResource)
            IdlingRegistry.getInstance().register(espressoTestIdlingResource)
        }
    }

    @Test
    fun test() {
        MainPageObject
            .tapFabButton()
            .tapFabButton() // Next perform doesn't start during the resource is idle.
            .assertFabButton()

        // Print view hierarchy
        scenarioRule.scenario.onActivity { activity ->
            print(myTestWatcher.getCurrentViewHierarchy(activity.window.decorView).toString())
        }
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
