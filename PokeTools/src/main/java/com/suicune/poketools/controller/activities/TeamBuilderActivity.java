package com.suicune.poketools.controller.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.PokemonTeam;
import com.suicune.poketools.view.fragments.teambuilder.TeamBuilderDrawerFragment;
import com.suicune.poketools.view.fragments.teambuilder.TeamMainFragment;
import com.suicune.poketools.view.fragments.teambuilder.TeamMemberFragment;

import java.util.HashMap;
import java.util.Map;

public class TeamBuilderActivity extends ActionBarActivity
		implements TeamBuilderDrawerFragment.TeamBuilderDrawerCallbacks,
		TeamMainFragment.OnTeamEditedListener, TeamMemberFragment.OnTeamMemberChangedListener {

	public static final String TEAM_EDIT_DRAWER_SELECTION = "team_editor_drawer_selection";
	private static final String FRAGMENT_TAG_TEAM_MEMBER = "fragment_team_member_";
	private static final String FRAGMENT_TAG_MAIN = "fragment_team_main";

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private TeamBuilderDrawerFragment mTeamBuilderDrawerFragment;
	private Map<Integer, TeamMemberFragment> teamFragments;
	private TeamMainFragment mainFragment;
	private int mCurrentFragment;

	private SharedPreferences prefs;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_builder);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		mTeamBuilderDrawerFragment = (TeamBuilderDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mTeamBuilderDrawerFragment
				.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		teamFragments = new HashMap<>();
		mCurrentFragment = prefs.getInt(TEAM_EDIT_DRAWER_SELECTION, 0);
		setMainFragment();
	}

	private void setMainFragment() {
		String tag;
		FragmentManager manager = getFragmentManager();
		Fragment fragment;
		switch (mCurrentFragment) {
			case 0:
				tag = FRAGMENT_TAG_MAIN;
				if (mainFragment == null) {
					mainFragment = (TeamMainFragment) manager.findFragmentByTag(tag);
					if (mainFragment == null) {
						mainFragment = TeamMainFragment.newInstance();
					}
				}
				fragment = mainFragment;
				break;
			default:
				tag = FRAGMENT_TAG_TEAM_MEMBER + mCurrentFragment;
				if (teamFragments.get(mCurrentFragment) == null) {
					TeamMemberFragment member;
					member = (TeamMemberFragment) manager.findFragmentByTag(tag);
					if (member == null) {
						member = TeamMemberFragment.newInstance(mCurrentFragment);
						teamFragments.put(mCurrentFragment, member);
					}
				}
				fragment = teamFragments.get(mCurrentFragment);
				break;
		}
		manager.beginTransaction().replace(R.id.team_builder_container, fragment, tag).commit();
		prefs.edit().putInt(TEAM_EDIT_DRAWER_SELECTION, mCurrentFragment).apply();
	}

	@Override
	public void onPokemonSelected(int position) {
		mCurrentFragment = position;
		setMainFragment();
	}

	@Override public void onMainScreenSelected() {
		mCurrentFragment = 0;
		setMainFragment();
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

	@Override public void onTeamChanged(PokemonTeam team) {
		mTeamBuilderDrawerFragment.onTeamChanged(team);
	}

	@Override public void onTeamMemberChanged(int position, Pokemon pokemon) {
		mTeamBuilderDrawerFragment.onMemberChanged(position, pokemon);
	}
}
