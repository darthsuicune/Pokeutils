package com.suicune.pokeutils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.Toast;

import com.suicune.pokeutils.compat.CompatTab;
import com.suicune.pokeutils.compat.CompatTabListener;
import com.suicune.pokeutils.compat.TabCompatActivity;
import com.suicune.pokeutils.compat.TabHelper;
import com.suicune.pokeutils.fragments.DamageCalcFragment;
import com.suicune.pokeutils.fragments.IVCalcFragment;
import com.suicune.pokeutils.fragments.PokedexFragment;
import com.suicune.pokeutils.fragments.TeamBuilderFragment;

public class MainActivity extends TabCompatActivity {
	private static final int TAB_IV_CALCULATOR = 0;
	private static final int TAB_DAMAGE_CALCULATOR = 1;
	private static final int TAB_TEAM_BUILDER = 2;
	private static final int TAB_POKEDEX = 3;

	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (prefs.getBoolean(SettingsActivity.FIRST_RUN, true)) {
			makeFirstRun();
		}
		setContentView(R.layout.main_activity);
		setTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void setTabs() {
		TabHelper tabHelper = getTabHelper();

		int defaultTab = prefs.getInt(SettingsActivity.DEFAULT_TAB,
				TAB_IV_CALCULATOR);

		createTab(tabHelper, R.string.tab_iv_calculator, new TabListener(this,
				IVCalcFragment.class));
		createTab(tabHelper, R.string.tab_damage_calculator, new TabListener(
				this, DamageCalcFragment.class));
		createTab(tabHelper, R.string.tab_team_builder, new TabListener(this,
				TeamBuilderFragment.class));
		createTab(tabHelper, R.string.tab_pokedex, new TabListener(this,
				PokedexFragment.class));

		tabHelper.setActiveTab(defaultTab);
	}

	private void createTab(TabHelper tabHelper, int textResourceId,
			TabListener listener) {

		CompatTab tab = tabHelper.newTab(getString(textResourceId));

		tab.setText(textResourceId);
		tab.setTabListener(listener);

		tabHelper.addTab(tab);
	}

	class TabListener implements CompatTabListener {
		private TabCompatActivity mActivity;
		private Class<? extends Fragment> mClass;

		protected TabListener(TabCompatActivity activity,
				Class<? extends Fragment> cls) {
			mActivity = activity;
			mClass = cls;
		}

		@Override
		public void onTabUnselected(CompatTab tab, FragmentTransaction ft) {
			Fragment fragment = tab.getFragment();
			if (fragment != null) {
				ft.detach(fragment);
				fragment.setHasOptionsMenu(false);
			}
		}

		@Override
		public void onTabSelected(CompatTab tab, FragmentTransaction ft) {
			if (getSupportFragmentManager().findFragmentByTag(tab.getTag()) != null) {
				tab.setFragment(getSupportFragmentManager().findFragmentByTag(
						tab.getTag()));
			}
			Fragment fragment = tab.getFragment();
			if (fragment == null) {
				fragment = Fragment.instantiate(mActivity, mClass.getName());
				tab.setFragment(fragment);
				ft.add(android.R.id.tabcontent, fragment, tab.getTag());
			} else {
				ft.attach(fragment);
			}
			fragment.setHasOptionsMenu(true);

			if (prefs.getBoolean(SettingsActivity.PREFERENCE_SAVE_TAB_DEFAULT,
					true)) {
				saveCurrentTabAsDefault(tab);
			}
		}

		@Override
		public void onTabReselected(CompatTab tab, FragmentTransaction ft) {
		}

	}

	private void saveCurrentTabAsDefault(CompatTab tab) {
		int currentTab = 0;
		if (tab.getTag().equals(getString(R.string.tab_iv_calculator))) {
			currentTab = TAB_IV_CALCULATOR;
		} else if (tab.getTag().equals(getString(R.string.tab_iv_calculator))) {
			currentTab = TAB_TEAM_BUILDER;
		} else if (tab.getTag().equals(getString(R.string.tab_pokedex))) {
			currentTab = TAB_POKEDEX;
		} else if (tab.getTag().equals(
				getString(R.string.tab_damage_calculator))) {
			currentTab = TAB_DAMAGE_CALCULATOR;
		}

		prefs.edit().putInt(SettingsActivity.DEFAULT_TAB, currentTab).commit();
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
}
