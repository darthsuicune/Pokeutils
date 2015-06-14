package com.suicune.poketools.view;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.suicune.poketools.R;
import com.suicune.poketools.controller.activities.MainActivity;
import com.suicune.poketools.model.Pokemon;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class PokemonCardViewTest {
	PokemonCardView cardView;
	PokemonCardHolder holder;
	Pokemon pokemon;

	@Rule public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

	@Before public void setUp() throws Exception {
		holder = mock(PokemonCardHolder.class);
	}

	@Test public void testSetupWithANullPokemonPreparesTheViews() throws Exception {
		setupCard();
		assertNotNull(cardView.nameAutoCompleteView);
		assertNotNull(cardView.nameView);
		assertNotNull(cardView.levelView);
		assertNotNull(cardView.abilityView);
		assertNotNull(cardView.cardDetailsView);
	}

	private void setupCard() {
		MainActivity activity = rule.getActivity();
		DrawerActions.openDrawer(R.id.drawer_layout);
		onView(withText(R.string.iv_calc_fragment_title)).perform(click());
		DrawerActions.closeDrawer(R.id.drawer_layout);
		cardView = (PokemonCardView) activity.findViewById(R.id.pokemon);
		cardView.cardHolder = holder;
	}

	@Test public void enableAttacksSetsTheVisibility() throws Throwable {
		setupCard();
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				cardView.enableAttacks();
			}
		});
		onView(withId(R.id.attacks)).check(matches(isDisplayed()));
	}

	@Test public void disableAttacksRemovesThemFromVisibility() throws Throwable {
		setupCard();
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				cardView.disableAttacks();
			}
		});
		onView(withId(R.id.attacks)).check(matches(not(isDisplayed())));
	}

	@Test public void gyaradosShowsItsData() throws Exception {
		setupCard();
		onView(withId(R.id.name)).perform(typeText("gya"));
		closeSoftKeyboard();
		Thread.sleep(1000);
		onView(withText("Gyarados")).perform(click());
		onView(Matchers.allOf(
				isDescendantOfA(withId(StatsView.BASE_STATS_ID)),
				withId(R.id.base_attack)
		)).check(matches(withText(125)));
	}

	@Test public void diancieShowsItsData() throws Exception {
		setupCard();
		onView(withId(R.id.name)).perform(typeText("dian"));
		closeSoftKeyboard();
		Thread.sleep(1000);
		onView(withText("Diancie")).perform(click());
		onView(Matchers.allOf(
				isDescendantOfA(withId(StatsView.BASE_STATS_ID)),
				withId(R.id.base_defense)
		)).check(matches(withText(150)));
	}
}