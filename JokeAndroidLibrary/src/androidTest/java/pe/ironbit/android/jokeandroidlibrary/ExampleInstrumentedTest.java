package pe.ironbit.android.jokeandroidlibrary;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static String JOKE_MESSAGE = "Joke Message";

    @Rule
    public ActivityTestRule<JokeAndroidActivity> activity =
            new ActivityTestRule<JokeAndroidActivity>(JokeAndroidActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = super.getActivityIntent();
                    intent.putExtra(JokeAndroidActivity.JOKE_ANDROID_LIBRARY_KEY, JOKE_MESSAGE);
                    return intent;
                }
            };

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("pe.ironbit.android.jokeandroidlibrary.test", appContext.getPackageName());
    }

    @Test
    public void ViewJokeMessage() {
        Espresso.onView(ViewMatchers.withId(R.id.joke_data))
                .check(ViewAssertions.matches(ViewMatchers.withText(JOKE_MESSAGE)));
    }
}
