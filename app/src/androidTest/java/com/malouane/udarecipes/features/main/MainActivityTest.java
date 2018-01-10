package com.malouane.udarecipes.features.main;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import com.malouane.udarecipes.R;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(MockitoJUnitRunner.class) public class MainActivityTest {

  @Rule public ActivityTestRule<MainActivity> activityActivityTestRule =
      new ActivityTestRule<>(MainActivity.class, true, true);
  private IdlingResource idlingResource;

  @Before public void setUp() {
    idlingResource = activityActivityTestRule.getActivity().getIdlingResource();
    IdlingRegistry.getInstance().register(idlingResource);

    activityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
  }

  @Test public void launchMainActivity() throws Exception {
    onView(withId(R.id.pb_loading)).check(matches(isDisplayed()));
  }

  @After public void tearDown() {
    if (idlingResource != null) {
      IdlingRegistry.getInstance().unregister(idlingResource);
    }
  }
}
