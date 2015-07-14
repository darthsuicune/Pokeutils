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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
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
		onView(allOf(withText(R.string.iv_calc_fragment_title),
				isDescendantOfA(withId(R.id.main_activity_toolbar)))).check(matches(isDisplayed()));
		onView(withId(R.id.iv_calc_results)).check(matches(isDisplayed()));
		verify(listener).onIvCalcRequested();
	}

	private void whenSelecting(int resId) throws InterruptedException {
		DrawerActions.openDrawer(R.id.main_activity_layout);
		onView(withId(resId)).perform(click());
	}

	@Test public void selectingTheTeamBuilderWorks() throws Exception {
		whenSelecting(R.id.team_builder);
		onView(allOf(withText(R.string.team_builder_fragment_title),
				isDescendantOfA(withId(R.id.main_activity_toolbar)))).check(matches(isDisplayed()));
		onView(withId(R.id.team_builder_create_new)).check(matches(isDisplayed()));
		onView(withId(R.id.team_builder_current_teams)).check(matches(isDisplayed()));
		verify(listener).onTeamBuilderRequested();
	}

	@Test public void selectingTheDamageCalcWorks() throws Exception {
		whenSelecting(R.id.damage_calc);
		onView(allOf(withText(R.string.damage_calc_fragment_title),
				isDescendantOfA(withId(R.id.main_activity_toolbar)))).check(matches(isDisplayed()));
		onView(withId(R.id.damage_calc_attacker)).check(matches(isDisplayed()));
		verify(listener).onDamageCalcRequested();
	}

	@Test public void selectingThePokedexWorks() throws Exception {
		whenSelecting(R.id.pokedex);
		onView(allOf(withText(R.string.pokedex_fragment_title),
				isDescendantOfA(withId(R.id.main_activity_toolbar)))).check(matches(isDisplayed()));
		onView(withId(R.id.pokemon_list)).check(matches(isDisplayed()));
		verify(listener).onDamageCalcRequested();
	}
}