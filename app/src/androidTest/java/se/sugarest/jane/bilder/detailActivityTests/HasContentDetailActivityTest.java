package se.sugarest.jane.bilder.detailActivityTests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import se.sugarest.jane.bilder.R;
import se.sugarest.jane.bilder.ui.DetailActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by jane on 17-11-23.
 */
@RunWith(AndroidJUnit4.class)
public class HasContentDetailActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityTestRule
            = new ActivityTestRule<>(DetailActivity.class);

    @Test
    public void detailScreenHasContent_ProgressBar() {
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
    }
}
