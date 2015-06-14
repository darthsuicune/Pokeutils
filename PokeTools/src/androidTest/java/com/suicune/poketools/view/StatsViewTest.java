package com.suicune.poketools.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.suicune.poketools.R;
import com.suicune.poketools.controller.activities.MainActivity;
import com.suicune.poketools.model.Stats;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class StatsViewTest {
	StatsView view;
	StatsView.OnStatsChangedListener listener;

	@Rule public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

	private void setupListener() {
		listener = mock(StatsView.OnStatsChangedListener.class);
		view.setup(listener);
	}

	@Test public void modifyingAStatCallsTheCallback() throws Exception {
		loadView();
		onView(allOf(
				withId(R.id.attack),
				isDescendantOfA(withId(StatsView.IVS_VIEW_ID))
		)).perform(typeText("28"));
		verify(listener).onStatChanged(Stats.StatType.IV, Stats.Stat.ATTACK, 28);
	}

	private void loadView() {
		view = (StatsView) rule.getActivity().findViewById(R.id.pokemon_stats_view);

		setupListener();
	}
}