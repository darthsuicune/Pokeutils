package com.suicune.poketools.view;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.suicune.poketools.R;
import com.suicune.poketools.controller.activities.MainActivity;
import com.suicune.poketools.model.gen6.Gen6Nature;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.suicune.poketools.model.gen6.Gen6Nature.BOLD;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class NatureViewTest {
	NatureView view;

	@Rule public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
	NatureView.OnNatureSelectedListener listener;

	@Test public void testSetNature() throws Throwable {
		loadView();
		view.setNature(BOLD);
		assertEquals(BOLD, view.getSelectedItem());
	}

	private void loadView() throws Throwable {
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				view = new NatureView(InstrumentationRegistry.getTargetContext());
			}
		});
		setupListener();
	}

	private void setupListener() throws Throwable {
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				listener = mock(NatureView.OnNatureSelectedListener.class);
				view.setup(listener);
			}
		});
	}

	@Test public void withANewSelectionTheCallbackIsCalled() throws Throwable {
		view = (NatureView) rule.getActivity().findViewById(R.id.nature);
		setupListener();
		onView(withId(R.id.nature)).perform(click());
		onView(withText(R.string.nature_serious)).perform(click());
		verify(listener).onNatureSelected(Gen6Nature.SERIOUS);
	}
}