package com.suicune.poketools.view.fragments;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.suicune.poketools.R;
import com.suicune.poketools.view.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class MainNavigationDrawerFragmentTest {

	@Rule public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

	MainActivity activity;
	MainNavigationDrawerFragment.MainNavigationDrawerCallbacks listener;
	MainNavigationDrawerFragment fragment;

	@Before public void setUp() throws Exception {
		activity = rule.getActivity();
		fragment = (MainNavigationDrawerFragment) activity.getFragmentManager()
				.findFragmentById(R.id.main_activity_drawer);
		listener = mock(MainNavigationDrawerFragment.MainNavigationDrawerCallbacks.class);
		fragment.listener = listener;
	}

	@Test public void selectingTheIvCalcWorks() throws Exception {
		whenSelecting(R.id.iv_calc);
		verify(listener).onIvCalcRequested();
	}

	private void whenSelecting(int resId) throws InterruptedException {
		DrawerActions.openDrawer(R.id.main_activity_layout);
		onView(withId(resId)).perform(click());
	}

	@Test public void selectingTheIvBreederCalcWorks() throws Exception {
		whenSelecting(R.id.iv_breeder_calc);
		verify(listener).onIvBreederRequested();
	}

	@Test public void selectingTheTeamBuilderWorks() throws Exception {
		whenSelecting(R.id.team_builder);
		verify(listener).onTeamBuilderRequested();
	}

	@Test public void selectingTheDamageCalcWorks() throws Exception {
		whenSelecting(R.id.damage_calc);
		verify(listener).onDamageCalcRequested();
	}

	@Test public void selectingThePokedexWorks() throws Exception {
		whenSelecting(R.id.pokedex);
		verify(listener).onPokedexRequested();
	}
}