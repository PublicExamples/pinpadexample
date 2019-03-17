package com.example.pinpad


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val button = onView(
            allOf(withId(R.id.button), isDisplayed())
        )
        val appCompatEditText = onView(
            allOf(
                withId(R.id.editText),
                isDisplayed()
            )
        )
        val generatedTextView = onView(
            allOf(
                withId(R.id.textView2),
                isDisplayed()
            )
        )
        button.check(
            matches(
                allOf(
                    isDisplayed(),
                    not(isEnabled())
                )
            )
        )


        appCompatEditText.perform(replaceText("1234"), closeSoftKeyboard())

        button.check(
            matches(
                allOf(
                    isDisplayed(),
                    isEnabled()
                )
            )
        )

        button.perform(click())

        generatedTextView.check(matches(withText(containsString("341216"))))
    }
}
