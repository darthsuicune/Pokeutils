package com.suicune.poketools.view.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.suicune.poketools.R;
import com.suicune.poketools.view.fragments.DamageCalcFragment;
import com.suicune.poketools.view.fragments.IvBreedingCalcFragment;
import com.suicune.poketools.view.fragments.IvCalcFragment;
import com.suicune.poketools.view.fragments.MainNavigationDrawerFragment;
import com.suicune.poketools.view.fragments.MainNavigationDrawerFragment.MainNavigationDrawerCallbacks;
import com.suicune.poketools.view.fragments.PokedexFragment;
import com.suicune.poketools.view.fragments.teambuilder.TeamBuilderFragment;
import com.suicune.poketools.view.fragments.teambuilder.TeamBuilderFragment.OnTeamBuilderInteractionListener;

public class MainActivity extends AppCompatActivity
		implements MainNavigationDrawerCallbacks, OnTeamBuilderInteractionListener {
	public static final String TAG_DAMAGE_CALC = "damageCalcFragment";
	public static final String TAG_IV_CALC = "ivCalcFragment";
	public static final String TAG_IV_BREEDER = "ivBreederFragment";
	public static final String TAG_POKEDEX = "pokedexFragment";
	public static final String TAG_TEAM_BUILDER = "teamBuilderFragment";

	MainNavigationDrawerFragment mainNavigationDrawerFragment;
	FragmentManager manager;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = getFragmentManager();
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
		setSupportActionBar(toolbar);
		mainNavigationDrawerFragment =
				(MainNavigationDrawerFragment) manager.findFragmentById(R.id.main_activity_drawer);
		mainNavigationDrawerFragment.setUp((DrawerLayout) findViewById(R.id.main_activity_layout),
				findViewById(R.id.main_activity_drawer), toolbar);
	}

	@Override public void onTeamBuilderRequested() {
		Fragment fragment = manager.findFragmentByTag(TAG_TEAM_BUILDER);
		if (fragment == null) {
			fragment = TeamBuilderFragment.newInstance();
		}
		setFragment(fragment, TAG_TEAM_BUILDER, R.string.team_builder_fragment_title);
	}

	private void setFragment(Fragment fragment, String tag, int titleId) {
		setTitle(titleId);
		manager.beginTransaction().replace(R.id.main_activity_fragment_container, fragment,
				tag).commit();
	}

	@Override public void onDamageCalcRequested() {
		Fragment fragment = manager.findFragmentByTag(TAG_DAMAGE_CALC);
		if (fragment == null) {
			fragment = DamageCalcFragment.newInstance();
		}
		setFragment(fragment, TAG_DAMAGE_CALC, R.string.damage_calc_fragment_title);
	}

	@Override public void onIvBreederRequested() {
		Fragment fragment = manager.findFragmentByTag(TAG_IV_BREEDER);
		if (fragment == null) {
			fragment = IvBreedingCalcFragment.newInstance();
		}
		setFragment(fragment, TAG_IV_BREEDER, R.string.iv_breeder_calc_fragment_title);
	}

	@Override public void onIvCalcRequested() {
		Fragment fragment = manager.findFragmentByTag(TAG_IV_CALC);
		if (fragment == null) {
			fragment = IvCalcFragment.newInstance();
		}
		setFragment(fragment, TAG_IV_CALC, R.string.iv_calc_fragment_title);
	}

	@Override public void onPokedexRequested() {
		Fragment fragment = manager.findFragmentByTag(TAG_POKEDEX);
		if (fragment == null) {
			fragment = PokedexFragment.newInstance();
		}
		setFragment(fragment, TAG_POKEDEX, R.string.pokedex_fragment_title);
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		//Show the global option, and if the drawer is open, pass the event to it
		getMenuInflater().inflate(R.menu.global, menu);
		return !mainNavigationDrawerFragment.isDrawerOpen() || super.onCreateOptionsMenu(menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
			default:
				// Pass the event to ActionBarDrawerToggle, if it returns true, then it has
				// handled the app icon touch event
				mainNavigationDrawerFragment.onOptionsItemSelected(item);
				return super.onOptionsItemSelected(item);
		}
	}

	@Override public void onNewTeamRequested() {

	}

	@Override public void onTeamSelected() {

	}
}
