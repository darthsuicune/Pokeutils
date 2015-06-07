package com.suicune.poketools.controller.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity
		implements MainNavigationDrawerFragment.NavigationDrawerCallbacks {
	public static final String TAG_DAMAGE_CALC = "damageCalcFragment";
	public static final String TAG_IV_CALC = "ivCalcFragment";
	public static final String TAG_IV_BREEDER = "ivBreederFragment";

	public static final int TEAM_BUILDER_SECTION = 0;
	public static final int DAMAGE_CALC_SECTION = 1;
	public static final int IV_BREED_SECTION = 2;
	public static final int IV_CALC_SECTION = 3;


	MainNavigationDrawerFragment mainNavigationDrawerFragment;
	CharSequence title;
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mainNavigationDrawerFragment = (MainNavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		title = getTitle();
		toolbar = (Toolbar) findViewById(R.id.main_toolbar);
		toolbar.setTitle(title);

		// Set up the drawer.
		mainNavigationDrawerFragment
				.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		if (position == TEAM_BUILDER_SECTION) {
			openTeamBuilder();
			return;
		}
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		Fragment fragment = null;
		String tag;
		switch (position) {
			case DAMAGE_CALC_SECTION:
				tag = TAG_DAMAGE_CALC;
				fragment = fragmentManager.findFragmentByTag(tag);
				if(fragment == null) {
					fragment = DamageCalcFragment.newInstance();
				}
				break;
			case IV_BREED_SECTION:
				tag = TAG_IV_BREEDER;
				fragment = fragmentManager.findFragmentByTag(tag);
				if(fragment == null) {
					fragment = IvBreedingCalcFragment.newInstance();
				}
				break;
			case IV_CALC_SECTION:
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
		if(fragment != null) {
			//fragment.setRetainInstance(true);
			fragmentManager.beginTransaction().replace(R.id.container, fragment, tag).commit();
			onSectionAttached(position);
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
			case 1:
				title = getString(R.string.damage_calc_fragment_title);
				break;
			case 2:
				title = getString(R.string.iv_breeder_calc_fragment_title);
				break;
			case 3:
				title = getString(R.string.iv_calc_fragment_title);
				break;
		}
		if (toolbar != null) {
			toolbar.setTitle(title);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mainNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen if the drawer is not
			// showing. Otherwise, let the drawer decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will automatically handle clicks on
		// the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
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
