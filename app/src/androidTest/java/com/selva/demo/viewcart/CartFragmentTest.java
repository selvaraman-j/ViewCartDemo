package com.selva.demo.viewcart;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.selva.demo.viewcart.utils.NetworkUtils;
import com.selva.demo.viewcart.view.ui.CartFragment;
import com.selva.demo.viewcart.view.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Class is used to test the ui of the application
 *
 * @author selva.raman
 * @version 1.0
 * @since 7/5/2018
 */

@RunWith(AndroidJUnit4.class)
public class CartFragmentTest {
    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * attach the cart fragment into activity
     */
    @Before
    public void setUp() {
        activityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction().add(R.id.container, CartFragment.getInstance()).commit();
    }

    /**
     * Checks whether the action bar is displayed or not
     */
    @Test
    public void testActionBarIsDisplayed() {
        onView(withText(activityTestRule.getActivity()
                .getString(R.string.app_name))).check(matches(isDisplayed()));
    }

    /**
     * Performs the pull to down refresh
     */
    @Test
    public void testPullDownRefresh() {
        //TODO to fix AmbiguousViewMatcherException
        onView(withId(R.id.swipe_refresh_layout)).perform(swipeDown());
    }

    /**
     * checks whether no internet connection! view is displayed or not
     * when the device is not connected to internet
     */
    @Test
    public void testNoInternetConnectionViewIsDisplayed() {
        if (!NetworkUtils.isNetworkConnected(activityTestRule.getActivity())) {
            onView(withText(activityTestRule.getActivity()
                    .getString(R.string.no_internet_connection))).check(matches(isDisplayed()));
        }
    }

    /**
     * Checks whether the continue button is displayed or not
     * and performs the click of continue button
     */
    @Test
    public void testContinueButtonClick() {
        onView(withId(R.id.button_continue)).check(matches(isDisplayed()));
        onView(withId(R.id.button_continue)).perform(click());
    }

    /**
     * Performs the orientation of device
     */
    @Test
    public void testOrientationChange() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;
        activityTestRule.getActivity().setRequestedOrientation(
                orientation == Configuration.ORIENTATION_PORTRAIT
                        ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
