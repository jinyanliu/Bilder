package se.sugarest.jane.bilder.mainActivityTests;

import android.app.Activity;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import se.sugarest.jane.bilder.R;
import se.sugarest.jane.bilder.ui.DetailActivity;
import se.sugarest.jane.bilder.ui.MainActivity;

import static android.app.Instrumentation.ActivityResult;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.IdlingRegistry.getInstance;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static se.sugarest.jane.bilder.util.Constants.INTENT_EXTRA_TITLE;

/**
 * Created by jane on 17-11-23.
 */
@RunWith(AndroidJUnit4.class)
public class RecyclerViewIntentVerificationMainActivityTest {

    private IdlingResource mIdlingResource;

    @Rule
    public IntentsTestRule<MainActivity> mIntentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new ActivityResult(Activity.RESULT_OK, null));
    }

    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = mIntentsTestRule.getActivity().getIdlingResource();
        getInstance().register(mIdlingResource);
    }

    @Test
    public void clickRecyclerViewItem_OpensDetailActivity() {
        onView(withId(R.id.editText)).perform(typeText("Flower"), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.recyclerview_photos)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(allOf(hasExtraWithKey(INTENT_EXTRA_TITLE),
                hasComponent(DetailActivity.class.getName())));
    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            getInstance().unregister(mIdlingResource);
        }
    }
}
