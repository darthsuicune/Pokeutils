package com.suicune.pokeutils.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.Toast;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.fragments.DamageCalcFragment;
import com.suicune.pokeutils.fragments.IVCalcFragment;
import com.suicune.pokeutils.fragments.PokedexFragment;
import com.suicune.pokeutils.fragments.TeamBuilderFragment;

public class MainActivity extends Activity implements ActionBar.TabListener {

	private SharedPreferences prefs;
	ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (prefs.getBoolean(SettingsActivity.FIRST_RUN, true)) {
			makeFirstRun();
		}
		setContentView(R.layout.main_activity);
		setTabs();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mActionBar.setDisplayShowTitleEnabled(false);
		} else {
			mActionBar.setDisplayShowTitleEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void setTabs() {
		int defaultTab = prefs.getInt(SettingsActivity.DEFAULT_TAB, 0);
		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mActionBar.addTab(createTab(R.string.tab_iv_calculator), false);
		mActionBar.addTab(createTab(R.string.tab_damage_calculator), false);
		mActionBar.addTab(createTab(R.string.tab_team_builder), false);
		mActionBar.addTab(createTab(R.string.tab_pokedex), false);

		mActionBar.setSelectedNavigationItem(defaultTab);
	}

	private ActionBar.Tab createTab(int resId) {
		ActionBar.Tab result = mActionBar.newTab();
		result.setContentDescription(resId);
		result.setTabListener(this);
		result.setText(resId);
		result.setTag(getString(resId));
		return result;
	}

	private void makeFirstRun() {
		Toast.makeText(this, R.string.first_run_load, Toast.LENGTH_LONG).show();
		new Thread(new FirstRunTask()).start();
	}

	private class FirstRunTask implements Runnable {

		public void run() {
			prefs.edit().putBoolean(SettingsActivity.FIRST_RUN, false).commit();
			return;
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment fragment = getFragmentManager().findFragmentByTag(
				tab.getText().toString());
		if (fragment == null) {
			if (tab.getText().toString()
					.equals(getString(R.string.tab_iv_calculator))) {
				fragment = Fragment.instantiate(this,
						IVCalcFragment.class.getName());
			} else if (tab.getText().toString()
					.equals(getString(R.string.tab_damage_calculator))) {
				fragment = Fragment.instantiate(this,
						DamageCalcFragment.class.getName());
			} else if (tab.getText().toString()
					.equals(getString(R.string.tab_team_builder))) {
				fragment = Fragment.instantiate(this,
						TeamBuilderFragment.class.getName());
			} else {
				fragment = Fragment.instantiate(this,
						PokedexFragment.class.getName());
			}
			ft.add(R.id.main_activity_container, fragment, tab.getText()
					.toString());
			prefs.edit()
					.putInt(SettingsActivity.DEFAULT_TAB,
							mActionBar.getSelectedNavigationIndex()).commit();
		} else {
			ft.attach(fragment);
		}
		fragment.setHasOptionsMenu(true);

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		Fragment fragment = getFragmentManager().findFragmentByTag(
				tab.getText().toString());
		fragment.setHasOptionsMenu(false);
		ft.detach(fragment);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Nothing to do here
	}
}
