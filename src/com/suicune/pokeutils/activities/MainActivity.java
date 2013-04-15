package com.suicune.pokeutils.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.Menu;
import android.widget.Toast;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.fragments.DamageCalcFragment;
import com.suicune.pokeutils.fragments.IVCalcFragment;
import com.suicune.pokeutils.fragments.PokedexFragment;
import com.suicune.pokeutils.fragments.TeamBuilderFragment;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private SharedPreferences prefs;
	ActionBar mActionBar;
	ViewPager mViewPager;
	TabsAdapter mPagerAdapter;

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

		setViewPager();
		
		int defaultTab = prefs.getInt(SettingsActivity.DEFAULT_TAB, 0);
		mActionBar.setSelectedNavigationItem(defaultTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void setTabs() {
		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mActionBar.addTab(createTab(R.string.tab_damage_calculator), false);
		mActionBar.addTab(createTab(R.string.tab_iv_calculator), false);
		mActionBar.addTab(createTab(R.string.tab_team_builder), false);
		mActionBar.addTab(createTab(R.string.tab_pokedex), false);
	}

	private ActionBar.Tab createTab(int resId) {
		ActionBar.Tab result = mActionBar.newTab();
		result.setContentDescription(resId);
		result.setTabListener(this);
		result.setText(resId);
		result.setTag(getString(resId));
		return result;
	}

	private void setViewPager() {
		mPagerAdapter = new TabsAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.main_activity_container);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (getActionBar() != null) {
					getActionBar().setSelectedNavigationItem(position);
				}
			}
		});
		mViewPager.setOffscreenPageLimit(3);
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
		if (mViewPager != null) {
			mViewPager.setCurrentItem(tab.getPosition());
		}
		prefs.edit().putInt(SettingsActivity.DEFAULT_TAB, tab.getPosition()).commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// Nothing to do here, managed by the view pager.
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Nothing to do here
	}

	public class TabsAdapter extends FragmentPagerAdapter {

		public TabsAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = Fragment.instantiate(MainActivity.this,
						DamageCalcFragment.class.getName());
				break;
			case 1:
				fragment = Fragment.instantiate(MainActivity.this,
						IVCalcFragment.class.getName());
				break;
			case 2:
				fragment = Fragment.instantiate(MainActivity.this,
						TeamBuilderFragment.class.getName());
				break;
			case 3:
				fragment = Fragment.instantiate(MainActivity.this,
						PokedexFragment.class.getName());
				break;
			default:
				break;
			}
			fragment.setHasOptionsMenu(true);
			return fragment;
		}

		@Override
		public int getCount() {
			return 4;
		}

	}
}
