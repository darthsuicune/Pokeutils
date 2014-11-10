package com.suicune.poketools.controller.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.suicune.poketools.R;
import com.suicune.poketools.view.fragments.DamageCalcFragment;
import com.suicune.poketools.view.fragments.IvBreedingCalcFragment;
import com.suicune.poketools.view.fragments.IvCalcFragment;
import com.suicune.poketools.view.fragments.NavigationDrawerFragment;

public class MainActivity extends Activity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	private static final String TAG_DAMAGE_CALC = "damageCalcFragment";
	private static final String TAG_IV_CALC = "ivCalcFragment";
	private static final String TAG_IV_BREEDER = "ivBreederFragment";

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		toolbar = (Toolbar) findViewById(R.id.main_toolbar);

		// Set up the drawer.
		mNavigationDrawerFragment
				.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		if (position == 0) {
			openTeamBuilder();
			return;
		}
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		Fragment fragment = null;
		String tag;
		switch (position) {
			case 1:
				tag = TAG_DAMAGE_CALC;
				fragment = fragmentManager.findFragmentByTag(tag);
				if(fragment == null) {
					fragment = DamageCalcFragment.newInstance();
				}
				break;
			case 2:
				tag = TAG_IV_BREEDER;
				fragment = fragmentManager.findFragmentByTag(tag);
				if(fragment == null) {
					fragment = IvBreedingCalcFragment.newInstance();
				}
				break;
			case 3:
				tag = TAG_IV_CALC;
				fragment = fragmentManager.findFragmentByTag(tag);
				if(fragment == null) {
					fragment = IvCalcFragment.newInstance();
				}
				break;
			default:
				tag = "";
				break;
		}
		fragment.setRetainInstance(true);
		fragmentManager.beginTransaction().replace(R.id.container, fragment, tag).commit();
		onSectionAttached(position);
	}

	public void onSectionAttached(int number) {
		switch (number) {
			case 1:
				mTitle = getString(R.string.damage_calc_fragment_title);
				break;
			case 2:
				mTitle = getString(R.string.iv_breeder_calc_fragment_title);
				break;
			case 3:
				mTitle = getString(R.string.iv_calc_fragment_title);
				break;
		}
		if (toolbar != null) {
			toolbar.setTitle(mTitle);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
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
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void openTeamBuilder() {
		Intent intent = new Intent(this, TeamBuilderActivity.class);
		startActivity(intent);
	}
}
