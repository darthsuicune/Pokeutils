package com.suicune.poketools.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.suicune.poketools.R;
import com.suicune.poketools.view.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.closeDrawer;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class PokemonCardViewTest {
	PokemonCardView cardView;
	PokemonCardHolder holder;

	@Rule public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
	MainActivity activity;

	@Before public void setUp() throws Exception {
		holder = mock(PokemonCardHolder.class);
		setupCard();
	}

	private void setupCard() {
		activity = rule.getActivity();
		openDrawer(R.id.drawer_layout);
		onView(withId(R.id.iv_calc)).perform(click());
		closeDrawer(R.id.drawer_layout);
		cardView = (PokemonCardView) activity.findViewById(R.id.pokemon);
		cardView.cardHolder = holder;
	}

	@Test public void testSetupWithANullPokemonPreparesTheViews() throws Exception {
		assertNotNull(cardView.nameAutoCompleteView);
		assertNotNull(cardView.nameHeaderView);
		assertNotNull(cardView.levelView);
		assertNotNull(cardView.abilityView);
		assertNotNull(cardView.cardDetailsView);
	}

	@Test public void enableAttacksSetsTheVisibility() throws Throwable {
		whenShowing("Gyarados");
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				cardView.enableAttacks();
			}
		});
		Thread.sleep(500);
		onView(withId(R.id.attacks)).check(matches(isDisplayed()));
	}

	private void whenShowing(String pokemon) throws InterruptedException {
		onView(withId(R.id.name)).perform(typeText(pokemon.substring(0, pokemon.length() - 2)));
		closeSoftKeyboard();
		Thread.sleep(500);
		onView(withText(pokemon)).inRoot(
				withDecorView(not(is(activity.getWindow().getDecorView()))))
				.perform(click());
	}

	@Test public void disableAttacksRemovesThemFromVisibility() throws Throwable {
		whenShowing("Gyarados");
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				cardView.disableAttacks();
			}
		});
		onView(withId(R.id.attacks)).check(matches(not(isDisplayed())));
	}

	@Test public void gyaradosShowsItsData() throws Exception {
		whenShowing("Gyarados");
		onView(allOf(isDescendantOfA(withId(StatsView.BASE_STATS_ID)), withId(R.id.base_attack)))
				.check(matches(withText("125")));
	}

	@Test public void diancieShowsItsData() throws Exception {
		whenShowing("Diancie");
		onView(allOf(isDescendantOfA(withId(StatsView.BASE_STATS_ID)), withId(R.id.base_defense)))
				.check(matches(withText("150")));
	}
}