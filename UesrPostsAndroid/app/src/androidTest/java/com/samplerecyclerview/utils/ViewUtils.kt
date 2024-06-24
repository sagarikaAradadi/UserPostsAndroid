package com.samplerecyclerview.utils

import android.util.Log
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.samplerecyclerview.base.TestBase.TAG
import junit.framework.Assert
import org.hamcrest.Matcher

@Throws(Exception::class)
fun waitForElement(
    viewMatcher: Matcher<View?>?, logMessage: String, timeoutInSecs: Int
): ViewInteraction? {
    var countTries = 1
    val noOfRetries: Int = timeoutInSecs * 15 / 2
    var isElementFound = false
    var viewInteraction: ViewInteraction? = null
    var assertionError: AssertionError? = null
    var noMatchingViewException: NoMatchingViewException? = null
    while (!(isElementFound || countTries > noOfRetries)) {
        noMatchingViewException = null
        assertionError = null
        try {
            viewInteraction = onView(viewMatcher, "Expected view")
            try {
                viewInteraction!!.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                isElementFound = true
                Log.d(TAG, " :: Found the element 1 : $logMessage")
            } catch (e: Throwable) {
                if (e is NoMatchingViewException || e is AssertionError) {
                    throw e
                }

                // for RootViewWithoutFocusException that happens during the activity transition
                // at this moment, it already passed 10 seconds so the activity transition is already finished.
                viewInteraction?.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                isElementFound = true
                Log.d(TAG, " :: Found the element 2: $logMessage")
            }
        } catch (e: NoMatchingViewException) {
            noMatchingViewException = e
            Log.d(
                TAG, "NoMatchingViewException :: Failed finding element for retry : $countTries"
            )
        } catch (e: AssertionError) {
            assertionError = e
            Log.d(
                TAG, "AssertionError :: Failed finding element for retry : $countTries"
            )
        }
        countTries++
    }
    return if (!isElementFound) {
        if (noMatchingViewException != null) {
            val errorString = noMatchingViewException.toString()
            throw Exception(
                logMessage + " Exception: " + limitExceptionStringLength(
                    errorString
                )
            )
        } else {
            val errorString = assertionError.toString()
            throw Exception(
                logMessage + " Exception: " + limitExceptionStringLength(
                    errorString
                )
            )
        }
    } else {
        viewInteraction
    }
}

fun onView(viewMatcher: Matcher<View?>?, message: String): ViewInteraction? {
        return try {
            try {
                onView(viewMatcher)
            } catch (e: RuntimeException) {
                if (e is NoMatchingViewException) {
                    val errorString = e.toString()
                    Assert.fail(
                        "Not found on UI " + message + limitExceptionStringLength(
                            errorString
                        )
                    )
                    return null
                }
                // when the code hit this line, it already passed 10 seconds that suspended in Espresso.onView
                Log.d(TAG, "onView NoMatchingRootException")
                onView(viewMatcher)
            }
        } catch (e: Throwable) {
            val errorString = e.toString()
            Assert.fail(
                "Not found on UI " + message + limitExceptionStringLength(
                    errorString
                )
            )
            null
        }
    }
    fun limitExceptionStringLength(errorString: String): String? {
        return if (errorString.length > 10000) {
            errorString.substring(0, 10000)
        } else errorString
    }

fun onRootView(): ViewInteraction? {
    return onView(ViewMatchers.isRoot(), "Root view")
}

fun perform(viewInteraction: ViewInteraction, message: String, vararg viewActions: ViewAction) {
    var count = 0
    var exception: Throwable? = null
    var isSuccessful = false
    while (count < 5) {
        try {
            viewInteraction.perform(*viewActions)
            // If action doesn't through any error, it indicates success
            isSuccessful = true
            Log.d(TAG, "Clicked on the element, worked: $viewActions")
            break
        } catch (e: Throwable) {
            exception = e
            Log.e(
                TAG, "Exception with perform action, reason: " + exception.message, exception
            )
        }
        count++
        Log.d(
            TAG, "Retrying to click on element: $viewActions with $count iterations"
        )
    }

    // Check status of the action
    if (!isSuccessful) {
        val actions = StringBuilder()
        for (viewAction in viewActions) {
            actions.append(viewAction.toString())
            actions.append(',')
        }

        // Assert and fail the test.
        val errorString = exception.toString()
        Assert.fail(
            "Not able to perform " + actions.toString() + message + limitExceptionStringLength(
                errorString
            )
        )
    }
    @Throws(java.lang.Exception::class)
    fun waitForElement(viewMatcher: Matcher<View?>?, logMessage: String?): ViewInteraction? {
        return logMessage?.let {
            waitForElement(
                viewMatcher,
                it,
                30
            )
        }
    }
}


