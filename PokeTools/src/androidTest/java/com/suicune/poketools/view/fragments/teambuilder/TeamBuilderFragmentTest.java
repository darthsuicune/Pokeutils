package com.suicune.poketools.view.fragments.teambuilder;

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
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class TeamBuilderFragmentTest {

	@Rule public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

	MainActivity activity;
	TeamBuilderFragment.OnTeamBuilderInteractionListener listener;
	TeamBuilderFragment fragment;

	@Before public void setUp() throws Exception {
		activity = rule.getActivity();
		openDrawer(R.id.main_activity_layout);
		onView(withId(R.id.team_builder)).perform(click());
		fragment = (TeamBuilderFragment) activity.getFragmentManager()
				.findFragmentByTag(MainActivity.TAG_TEAM_BUILDER);
	}

	@Test public void afterActivityCreationEverythingWorks() throws Exception {
		assertNotNull(fragment.listener);
		assertNotNull(fragment.teamList);
	}

	@Test public void onTapCreationOfNewTeamCallTheCallback() throws Exception {
		mockListener();
		onView(withId(R.id.team_builder_create_new)).perform(click());
		verify(listener).onNewTeamRequested();
	}

	private void mockListener() {
		listener = mock(TeamBuilderFragment.OnTeamBuilderInteractionListener.class);
		fragment.listener = listener;
	}
}