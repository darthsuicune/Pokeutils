package com.suicune.poketools.view.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class TeamBuilderActivity extends AppCompatActivity
		implements TeamBuilderDrawerFragment.TeamBuilderDrawerCallbacks,
		TeamMainFragment.OnTeamEditedListener, TeamMemberFragment.OnTeamMemberChangedListener {

	public static final String TEAM_EDIT_DRAWER_SELECTION = "team_editor_drawer_selection";
	private static final String FRAGMENT_TAG_TEAM_MEMBER = "fragment_team_member_";
	private static final String FRAGMENT_TAG_MAIN = "fragment_team_main";

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private TeamBuilderDrawerFragment teamBuilderDrawerFragment;
	private Map<Integer, TeamMemberFragment> teamFragments;
	private TeamMainFragment mainFragment;
	private int currentFragment;

	private SharedPreferences prefs;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_builder);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		currentFragment = prefs.getInt(TEAM_EDIT_DRAWER_SELECTION, 0);
		teamFragments = new HashMap<>();

		Toolbar toolbar = (Toolbar) findViewById(R.id.team_builder_toolbar);
		setSupportActionBar(toolbar);

		// Set up the drawer.
		teamBuilderDrawerFragment = (TeamBuilderDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		teamBuilderDrawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout),
				findViewById(R.id.navigation_drawer), toolbar);

		setMainFragment();
	}

	private void setMainFragment() {
		String tag;
		FragmentManager manager = getFragmentManager();
		Fragment fragment;
		switch (currentFragment) {
			case 0:
				tag = FRAGMENT_TAG_MAIN;
				if (mainFragment == null) {
					mainFragment = (TeamMainFragment) manager.findFragmentByTag(tag);
					if (mainFragment == null) {
						mainFragment = TeamMainFragment.newInstance();
						mainFragment.setRetainInstance(true);
					}
				}
				fragment = mainFragment;
				break;
			default:
				tag = FRAGMENT_TAG_TEAM_MEMBER + currentFragment;
				if (teamFragments.get(currentFragment) == null) {
					TeamMemberFragment member = (TeamMemberFragment) manager.findFragmentByTag(tag);
					if (member == null) {
						member = TeamMemberFragment.newInstance(currentFragment);
						member.setRetainInstance(true);
					}
					teamFragments.put(currentFragment, member);
				}
				fragment = teamFragments.get(currentFragment);
				break;
		}
		manager.beginTransaction().replace(R.id.team_builder_container, fragment, tag).commit();
		prefs.edit().putInt(TEAM_EDIT_DRAWER_SELECTION, currentFragment).apply();
	}

	@Override public void onPokemonSelected(int position) {
		currentFragment = position;
		setMainFragment();
	}

	@Override public void onMainScreenSelected() {
		currentFragment = 0;
		setMainFragment();
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		if (!teamBuilderDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.global, menu);
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override public void onTeamChanged(PokemonTeam team) {
		teamBuilderDrawerFragment.onTeamChanged(team);
	}

	@Override public void onTeamMemberChanged(int position, Pokemon pokemon) {
		teamBuilderDrawerFragment.onMemberChanged(position, pokemon);
	}
}
