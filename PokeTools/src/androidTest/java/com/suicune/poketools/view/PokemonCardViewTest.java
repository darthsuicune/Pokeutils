package com.suicune.poketools.view;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.suicune.poketools.R;
import com.suicune.poketools.controller.activities.MainActivity;
import com.suicune.poketools.model.Pokemon;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
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
		for (View v : cardView.attackViews.values()) {
			assertEquals(v.getVisibility(), View.VISIBLE);
		}
	}

	@Test public void disableAttacksRemovesThemFromVisibility() throws Throwable {
		setupCard();

		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				cardView.disableAttacks();
			}
		});
		for (View v : cardView.attackViews.values()) {
			assertEquals(v.getVisibility(), View.GONE);
		}
	}
}