package com.thegreychain.kotlinclculator

import android.app.PendingIntent.getActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.After
import org.junit.Before
import org.junit.runners.MethodSorters
import org.junit.FixMethodOrder


@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HelloWorldEspressoTest {

    @get:Rule
    public val mActivityRule = ActivityTestRule(
            CalculatorActivity::class.java)


    @Before
    @Throws(Exception::class)
    fun setUp() {
        //Before Test case execution
    }

    @Test
    fun test1ChatId() {

        onView(withId(R.id.lbl_display)).check(matches(isDisplayed()))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        //After Test case Execution
    }
}