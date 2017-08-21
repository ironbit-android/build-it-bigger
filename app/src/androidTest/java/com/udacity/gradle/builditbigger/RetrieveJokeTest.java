package com.udacity.gradle.builditbigger;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;

@RunWith(AndroidJUnit4.class)
public class RetrieveJokeTest {

    private class IdlingResourceHelper implements IdlingResource {
        private ResourceCallback callback;

        private AtomicBoolean state = new AtomicBoolean(true);

        @Override
        public String getName() {
            return getClass().getName();
        }

        @Override
        public boolean isIdleNow() {
            return state.get();
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {
            this.callback = callback;
        }

        public void setState(boolean value) {
            state.set(value);
            if (value && callback != null) {
                callback.onTransitionToIdle();
            }
        }
    }

    private class BackendListenerHelper implements BackendAsyncTask.BackendListener {
        private IdlingResourceHelper resource;

        public BackendListenerHelper(IdlingResource resource) {
            this.resource = (IdlingResourceHelper) resource;
        }

        @Override
        public void onAction() {
            resource.setState(true);
        }
    }

    @Rule
    public ActivityTestRule<MainActivity> activity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void verifyNotNullString() {
        IdlingResource idlingResource = new IdlingResourceHelper();

        BackendAsyncTask.BackendListener listener = new BackendListenerHelper(idlingResource);

        activity.getActivity().setBackendListener(listener);

        Espresso.onView(ViewMatchers.withId(R.id.gce_joke))
                .perform(ViewActions.click());

        ((IdlingResourceHelper) idlingResource).setState(false);

        Espresso.onView(ViewMatchers.withId(R.id.joke_data))
                .check(ViewAssertions.matches(ViewMatchers.withText("Joke from Java Library and from Cloud")));
    }
}
