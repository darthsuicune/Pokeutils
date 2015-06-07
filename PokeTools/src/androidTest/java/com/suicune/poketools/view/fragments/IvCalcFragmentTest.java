package com.suicune.poketools.view.fragments;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.suicune.poketools.R;
import com.suicune.poketools.controller.activities.MainActivity;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.factories.PokemonFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class IvCalcFragmentTest {
	Pokemon pokemon;
	IvCalcFragment fragment;

	@Rule public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

	@Before public void setUp() throws Exception {
		pokemon = PokemonFactory
				.createPokemon(InstrumentationRegistry.getTargetContext(), 6, 381, 0, 30);
	}

	@Test public void testUpdatePokemonDisplaysTheIvs() throws Throwable {
		getFragment();
		onView(allOf(isDescendantOfA(withId(R.id.results)), withId(R.id.hp))).check(
				matches(withText("30 - 31")));
		onView(allOf(isDescendantOfA(withId(R.id.results)), withId(R.id.attack)))
				.check(matches(withText("30 - 31")));
		onView(allOf(isDescendantOfA(withId(R.id.results)), withId(R.id.speed)))
				.check(matches(withText("30 - 31")));
	}

	private void getFragment() throws Throwable {
		MainActivity activity = rule.getActivity();
		DrawerActions.openDrawer(R.id.drawer_layout);
		onView(withText(R.string.iv_calc_fragment_title)).perform(click());
		DrawerActions.closeDrawer(R.id.drawer_layout);
		fragment = (IvCalcFragment) activity.getFragmentManager()
				.findFragmentByTag(MainActivity.TAG_IV_CALC);
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				fragment.updatePokemon(pokemon);
			}
		});
	}
}