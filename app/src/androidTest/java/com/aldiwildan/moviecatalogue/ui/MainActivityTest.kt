package com.aldiwildan.moviecatalogue.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.aldiwildan.moviecatalogue.R
import com.aldiwildan.moviecatalogue.utils.EspressoIdlingResource
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
    }

    @Test
    fun loadMovieDetail() {
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                5,
                click()
            )
        )
        onView(allOf(withId(R.id.img_poster), isDisplayed()))
        onView(allOf(withId(R.id.tv_item_title), isDisplayed()))
        onView(allOf(withId(R.id.tv_item_rating), isDisplayed()))
        onView(allOf(withId(R.id.tv_item_release), isDisplayed()))
        onView(allOf(withId(R.id.tv_item_overview_value), isDisplayed()))
        onView(withId(R.id.menu_item_favorite)).perform(click())
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.vp_main)).perform(swipeLeft())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
    }

    @Test
    fun loadTvShowDetail() {
        onView(withId(R.id.vp_main)).perform(swipeLeft())
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(allOf(withId(R.id.img_poster), isDisplayed()))
        onView(allOf(withId(R.id.tv_item_title), isDisplayed()))
        onView(allOf(withId(R.id.tv_item_rating), isDisplayed()))
        onView(allOf(withId(R.id.tv_item_release), isDisplayed()))
        onView(allOf(withId(R.id.tv_item_overview_value), isDisplayed()))
    }

    @Test
    fun loadFavoriteMovie() {
        for (x in 0 until 2) {
            onView(withId(R.id.vp_main)).perform(swipeLeft())
        }
        onView(withId(R.id.rv_favorite_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                1
            )
        )
    }

    @Test
    fun loadFavoriteTvShow() {
        for (x in 0 until 3) {
            onView(withId(R.id.vp_main)).perform(swipeLeft())
        }
        onView(withId(R.id.rv_favorite_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_tv_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                1
            )
        )
    }
}
