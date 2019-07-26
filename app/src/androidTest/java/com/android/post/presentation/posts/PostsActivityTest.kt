package com.android.post.presentation.posts

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.android.post.R
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PostsActivityTest {

    private val DELAY_TIME = 1000L
    @get:Rule
    val activityRule = ActivityTestRule(PostsActivity::class.java)

    @Test
    fun filterEditText_IsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.posts_filter_edit_text))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun postsRecyclerView_IsVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.posts_recycler_view))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.activity.window.decorView)
                )
            )
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun postsRecyclerView_scrollToEnd() {
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.posts_recycler_view)
        val itemCount = recyclerView.adapter?.itemCount

        Espresso.onView(ViewMatchers.withId(R.id.posts_recycler_view))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.activity.window.decorView)
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount ?: 1 - 1))
    }

    @Test
    fun postsRecyclerViewItem_OnClick() {
        Thread.sleep(DELAY_TIME)//To make a delay to receive data before clicking
        Espresso.onView(ViewMatchers.withId(R.id.posts_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
    }

}