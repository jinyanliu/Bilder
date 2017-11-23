package se.sugarest.jane.bilder.mainActivityTests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import se.sugarest.jane.bilder.R;
import se.sugarest.jane.bilder.ui.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by jane on 17-11-23.
 */
@RunWith(AndroidJUnit4.class)
public class HasContentMainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private Context instrumentationCtx;

    @Before
    public void setUp() {
        instrumentationCtx = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void mainScreenHasContent_EditTextView() {
        onView(withId(R.id.editText)).check(matches(isDisplayed()));
    }

    @Test
    public void mainScreenHasContent_SearchButton() {
        onView(withId(R.id.button)).check(matches(isDisplayed()))
                .check(matches(withText(instrumentationCtx.getString(R.string.button_text_search))));
    }

    @Test
    public void mainScreenHasContent_RecyclerView() {
        onView(withId(R.id.recyclerview_photos)).check(matches(isDisplayed()));
    }
}
