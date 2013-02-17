package com.suicune.pokeutils;

import android.content.ContentValues;
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
import com.suicune.pokeutils.database.PokeContract;

public class MainActivity extends TabCompatActivity {
	private static final int TAB_CALCULATORS = 0;
	private static final int TAB_TEAM_BUILDER = 1;
	private static final int TAB_GENERAL = 2;
	private static final int TAB_TABLES = 3;

	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
//		makeFirstInsert();
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

		int defaultTab = prefs
				.getInt(SettingsActivity.DEFAULT_TAB, TAB_CALCULATORS);

		createTab(tabHelper, getString(R.string.iv_calculator), R.string.iv_calculator,
				new TabListener(this, IVCalcFragment.class));
		createTab(tabHelper, getString(R.string.team_builder), R.string.team_builder,
				new TabListener(this, TeamBuilderFragment.class));

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
			currentTab = TAB_CALCULATORS;
		} else if (tab.getTag().equals(getString(R.string.iv_calculator))) {
			currentTab = TAB_TEAM_BUILDER;
		} else {
		}

		prefs.edit().putInt(SettingsActivity.DEFAULT_TAB, currentTab).commit();
	}

	private void makeFirstInsert() {
		ContentValues values = new ContentValues();
		values.put(PokeContract.PokemonTable.POKEMON_NAME, "Bulbasaur");
		values.put(PokeContract.PokemonTable.POKEMON_NUMBER, "1");
		values.put(PokeContract.PokemonTable.TYPE_1, "Grass");
		values.put(PokeContract.PokemonTable.TYPE_2, "Poison");
		values.put(PokeContract.PokemonTable.ABILITY_1, "Clorophyl");
		values.put(PokeContract.PokemonTable.ABILITY_2, "-");
		values.put(PokeContract.PokemonTable.ABILITY_DW, "Overgrow");
		values.put(PokeContract.PokemonTable.BASE_STAT_HP, "45");
		values.put(PokeContract.PokemonTable.BASE_STAT_ATT, "49");
		values.put(PokeContract.PokemonTable.BASE_STAT_DEF, "49");
		values.put(PokeContract.PokemonTable.BASE_STAT_SPATT, "65");
		values.put(PokeContract.PokemonTable.BASE_STAT_SPDEF, "65");
		values.put(PokeContract.PokemonTable.BASE_STAT_SPEED, "45");
		values.put(PokeContract.PokemonTable.BASE_EV_AMOUNT, "1");
		values.put(PokeContract.PokemonTable.BASE_EV_TYPE, "SpAtt");
		getContentResolver().insert(PokeContract.CONTENT_POKEMON, values);
		
		values.clear();
		values.put(PokeContract.NaturesTable.NATURE_NAME, "Timid");
		values.put(PokeContract.NaturesTable.STAT_DOWN, "Attack");
		values.put(PokeContract.NaturesTable.STAT_UP, "Speed");
		getContentResolver().insert(PokeContract.CONTENT_NATURE, values);
		values.clear();
		values.put(PokeContract.NaturesTable.NATURE_NAME, "Adamant");
		values.put(PokeContract.NaturesTable.STAT_DOWN, "SpecialAttack");
		values.put(PokeContract.NaturesTable.STAT_UP, "Attack");
		getContentResolver().insert(PokeContract.CONTENT_NATURE, values);
	}
}
