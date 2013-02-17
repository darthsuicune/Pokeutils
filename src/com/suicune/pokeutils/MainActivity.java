package com.suicune.pokeutils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.suicune.pokeutils.compat.CompatTab;
import com.suicune.pokeutils.compat.CompatTabListener;
import com.suicune.pokeutils.compat.TabCompatActivity;
import com.suicune.pokeutils.compat.TabHelper;

public class MainActivity extends TabCompatActivity {
	private static final int TAB_GENERAL = 0;
	private static final int TAB_CALCULATORS = 1;
	private static final int TAB_TABLES = 2;
	
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.main_activity);
		setTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void setTabs() {
		TabHelper tabHelper = getTabHelper();
		
		int defaultTab = prefs.getInt(SettingsActivity.DEFAULT_TAB,
                TAB_GENERAL);
		
		createTab(tabHelper, "IV Calculator", R.string.iv_calculator, new TabListener(this, IVCalcFragment.class));
		
		tabHelper.setActiveTab(defaultTab);
	}

	private void createTab(TabHelper tabHelper, String tag, int textResourceId,
			TabListener listener) {

		CompatTab tab = tabHelper.newTab(tag);

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
			}
			fragment.setHasOptionsMenu(false);
		}

		@Override
		public void onTabSelected(CompatTab tab, FragmentTransaction ft) {
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
		if (tab.getTag().equals(getString(R.string.iv_calculator))) {
		} else {
		}

		prefs.edit().putInt(SettingsActivity.DEFAULT_TAB, currentTab).commit();
	}
}
