package com.samplerecyclerview.pages

import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.samplerecyclerview.R
import com.samplerecyclerview.base.InPage
import com.samplerecyclerview.utils.waitForElement
import org.hamcrest.core.AllOf.allOf


open class PostScreenPage: InPage {
    override fun navigate() {
        //navigate to specific screen
        TODO("Not yet implemented")
    }

    override fun validate() {
        //validate the common screen options
        TODO("Not yet implemented")
    }

    fun verifyPostRecyclerView() {
        // Find a view and perform an action
        waitForElement(
            allOf(
                ViewMatchers.withId(R.id.postRv), ViewMatchers.isDisplayed()
            ),
            "call option not found",30)?.perform(ViewActions.click())
    }
}