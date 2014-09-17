package com.suicune.poketools.controller.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.suicune.poketools.R;
import com.suicune.poketools.view.fragments.teambuilder.TeamBuilderDrawerFragment;
import com.suicune.poketools.view.fragments.teambuilder.TeamMainFragment;
import com.suicune.poketools.view.fragments.teambuilder.TeamMemberFragment;

import java.util.HashMap;
import java.util.Map;

public class TeamBuilderActivity extends ActionBarActivity
		implements TeamBuilderDrawerFragment.TeamBuilderDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private TeamBuilderDrawerFragment mTeamBuilderDrawerFragment;
	private Map<Integer, TeamMemberFragment> teamFragments;
	private TeamMainFragment mainFragment;
	private int mCurrentFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_builder);

		mTeamBuilderDrawerFragment = (TeamBuilderDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mTeamBuilderDrawerFragment
				.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		teamFragments = new HashMap<>();
	}

	@Override
	public void onPokemonSelected(int position) {
		FragmentTransaction transaction = null;
		String tag = null;

		if (position != mCurrentFragment) {
			transaction = getFragmentManager().beginTransaction();
			if (teamFragments.containsKey(position)) {

			} else {
				transaction = getFragmentManager().beginTransaction();
				TeamMemberFragment fragment = TeamMemberFragment.newInstance(position);
				teamFragments.put(position, fragment);
			}
		}
		if (transaction != null) {
			transaction.replace(R.id.team_builder_container, teamFragments.get(position), tag)
					.commit();
		}
		mCurrentFragment = position;
	}

	@Override public void onMainScreenSelected() {
		if (mCurrentFragment != 0) {
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			if (mainFragment == null) {
				mainFragment = TeamMainFragment.newInstance();
			}
			transaction.replace(R.id.team_builder_container, mainFragment).commit();
		}

		mCurrentFragment = 0;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mTeamBuilderDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.team_builder, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
