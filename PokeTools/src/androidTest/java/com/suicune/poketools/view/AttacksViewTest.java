package com.suicune.poketools.view;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Spinner;

import com.suicune.poketools.R;
import com.suicune.poketools.view.activities.MainActivity;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.factories.AttackFactory;
import com.suicune.poketools.view.AttacksView.OnAttacksChangedListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class AttacksViewTest {
	Context context;
	AttacksView view;
	OnAttacksChangedListener listener;
	List<Attack> attacks = new ArrayList<>();
	MainActivity activity;

	@Rule public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

	@Before public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test public void afterAssigningAttacksTheyAppearOnTheSpinners() throws Throwable {
		createView();
		assignSomeAttacks();
		Spinner spinner = (Spinner) view.findViewById(AttacksView.ATTACK_1_SPINNER_ID);
		assertEquals(attacks.get(0), spinner.getAdapter().getItem(0));
	}

	private void createView() {
		view = new AttacksView(InstrumentationRegistry.getTargetContext());
		setupListener();
	}

	private void setupListener() {
		listener = Mockito.mock(OnAttacksChangedListener.class);
		view.setup(listener);
	}

	private void assignSomeAttacks() throws Throwable  {
		attacks.add(AttackFactory.create(context, 6, 12));
		attacks.add(AttackFactory.create(context, 6, 20));
		attacks.add(AttackFactory.create(context, 6, 47));

		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				view.setAttacks(attacks);
			}
		});
	}

	@Test public void changingTheAttackCallsTheListener() throws Throwable {
		loadView();
		assignSomeAttacks();
		onView(withId(AttacksView.ATTACK_1_SPINNER_ID)).perform(click());
		onView(withText(attacks.get(1).toString())).perform(click());
		verify(listener).onAttackChanged(0, attacks.get(1));
	}

	private void loadView() throws Throwable {
		activity = rule.getActivity();
		view = (AttacksView) activity.findViewById(R.id.attacks);

		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				((PokemonCardView) activity.findViewById(R.id.pokemon)).enableAttacks();
			}
		});
		setupListener();
	}
}