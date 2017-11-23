package se.sugarest.jane.bilder.mainActivityTests;

import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import se.sugarest.jane.bilder.R;
import se.sugarest.jane.bilder.ui.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.IdlingRegistry.getInstance;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by jane on 17-11-2.
 */
@RunWith(AndroidJUnit4.class)
public class WriteOnEditTextMainActivityTest {

    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        getInstance().register(mIdlingResource);
    }

    @Test
    public void mainActivitySearchEditTextTest() {
        onView(withId(R.id.editText)).perform(typeText("Flower"), closeSoftKeyboard())
                .check(matches(withText("Flower")));

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.recyclerview_photos)).check(matches(notNullValue()));
    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            getInstance().unregister(mIdlingResource);
        }
    }
}
