package com.samplerecyclerview.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.samplerecyclerview.pages.PostScreenPage
import com.samplerecyclerview.ui.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PostScreenTest {
    private lateinit var page: PostScreenPage


    @Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testButtonClick() {
        page = PostScreenPage()
        // Find a view and perform an action and checking the view  if a view is displayed
        page.verifyPostRecyclerView()
    }
}