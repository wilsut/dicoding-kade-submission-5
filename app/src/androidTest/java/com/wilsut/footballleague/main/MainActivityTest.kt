package com.wilsut.footballleague.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import com.wilsut.footballleague.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        onView(withId(R.id.list_league))
            .check(matches(isDisplayed()))
        onView(withId(R.id.list_league)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )
        onView(withId(android.R.id.tabhost)).check(matches(isDisplayed()))
        onView(withText("Search Match"))
            .perform(scrollTo())
            .perform(click())
        onView(withId(R.id.search_event)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.search_event)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.search_event)).perform(typeText("chelsea"))
        Thread.sleep(1000)
        onView(withId(R.id.search_event)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(3000)
    }
}