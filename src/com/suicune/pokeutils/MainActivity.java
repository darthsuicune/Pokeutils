package com.suicune.pokeutils;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
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
import com.suicune.pokeutils.database.PokeContract;
import com.suicune.pokeutils.fragments.IVCalcFragment;
import com.suicune.pokeutils.fragments.PokedexFragment;
import com.suicune.pokeutils.fragments.TeamBuilderFragment;
import com.suicune.pokeutils.tools.DBReader;

public class MainActivity extends TabCompatActivity {
	private static final int TAB_CALCULATORS = 0;
	private static final int TAB_TEAM_BUILDER = 1;
	private static final int TAB_POKEDEX = 1;

	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (prefs.getBoolean(SettingsActivity.FIRST_RUN, true)) {
			makeFirstRun();
			prefs.edit().putBoolean(SettingsActivity.FIRST_RUN, false).commit();
		}
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
				TAB_CALCULATORS);

		createTab(tabHelper, getString(R.string.tab_iv_calculator),
				R.string.tab_iv_calculator, new TabListener(this,
						IVCalcFragment.class));
		createTab(tabHelper, getString(R.string.tab_team_builder),
				R.string.tab_team_builder, new TabListener(this,
						TeamBuilderFragment.class));
		createTab(tabHelper, getString(R.string.tab_pokedex),
				R.string.tab_pokedex, new TabListener(this,
						PokedexFragment.class));

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
		if (tab.getTag().equals(getString(R.string.tab_iv_calculator))) {
			currentTab = TAB_CALCULATORS;
		} else if (tab.getTag().equals(getString(R.string.tab_iv_calculator))) {
			currentTab = TAB_TEAM_BUILDER;
		} else if (tab.getTag().equals(getString(R.string.tab_pokedex))) {
			currentTab = TAB_POKEDEX;
		}

		prefs.edit().putInt(SettingsActivity.DEFAULT_TAB, currentTab).commit();
	}

	private void makeFirstRun() {

		Toast.makeText(this, R.string.first_run_load, Toast.LENGTH_LONG).show();

		 new Thread(new InsertDataTask(this)).start();
	}

	 private class InsertDataTask implements Runnable {
		private Context mContext;

		public InsertDataTask(Context context) {
			mContext = context;
			run();
		}

		public void run() {
			loadNatures();
			loadPokemon();
			loadAbilities();
			return;
		}

		public void loadPokemon() {
			ArrayList<String> elements = new ArrayList<String>();
			elements.add(PokeContract.PokemonName.NUMBER);
			elements.add(PokeContract.PokemonName.FORM);
			elements.add(PokeContract.PokemonName.NAME);
			elements.add(PokeContract.PokemonType1.TYPE);
			elements.add(PokeContract.PokemonType2.TYPE);
			elements.add(PokeContract.PokemonBaseStats.BASE_HP);
			elements.add(PokeContract.PokemonBaseStats.BASE_ATT);
			elements.add(PokeContract.PokemonBaseStats.BASE_DEF);
			elements.add(PokeContract.PokemonBaseStats.BASE_SPATT);
			elements.add(PokeContract.PokemonBaseStats.BASE_SPDEF);
			elements.add(PokeContract.PokemonBaseStats.BASE_SPEED);
			elements.add(PokeContract.PokemonAbility1.ABILITY_1);
			elements.add(PokeContract.PokemonAbility2.ABILITY_2);
			elements.add(PokeContract.PokemonAbilityDW.ABILITY_DW);

			ArrayList<HashMap<String, String>> result = DBReader.readDB(
					getResources().openRawResource(R.raw.pokemon), elements);
			ContentValues[] pokemons = new ContentValues[result.size()];
			ContentValues[] stats = new ContentValues[result.size()];
			ContentValues[] type1s = new ContentValues[result.size()];
			ContentValues[] type2s = new ContentValues[result.size()];
			ContentValues[] ability1s = new ContentValues[result.size()];
			ContentValues[] ability2s = new ContentValues[result.size()];
			ContentValues[] abilitydws = new ContentValues[result.size()];
			for (int i = 0; i < result.size(); i++) {
				ContentValues pokemonValues = new ContentValues();
				ContentValues pokemonStatsValues = new ContentValues();
				ContentValues type1Values = new ContentValues();
				ContentValues type2Values = new ContentValues();
				ContentValues ability1Values = new ContentValues();
				ContentValues ability2Values = new ContentValues();
				ContentValues abilityDWValues = new ContentValues();
				HashMap<String, String> pokemon = result.get(i);
				String number = pokemon.get(PokeContract.PokemonName.NUMBER);
				String form = pokemon.get(PokeContract.PokemonName.FORM);
				String name = pokemon.get(PokeContract.PokemonName.NAME);
				String type1 = pokemon.get(PokeContract.PokemonType1.TYPE);
				String type2 = pokemon.get(PokeContract.PokemonType2.TYPE);
				String ability1 = pokemon
						.get(PokeContract.PokemonAbility1.ABILITY_1);
				String ability2 = pokemon
						.get(PokeContract.PokemonAbility2.ABILITY_2);
				String abilityDW = pokemon
						.get(PokeContract.PokemonAbilityDW.ABILITY_DW);
				String baseHP = pokemon
						.get(PokeContract.PokemonBaseStats.BASE_HP);
				String baseAtt = pokemon
						.get(PokeContract.PokemonBaseStats.BASE_ATT);
				String baseDef = pokemon
						.get(PokeContract.PokemonBaseStats.BASE_DEF);
				String baseSpAtt = pokemon
						.get(PokeContract.PokemonBaseStats.BASE_SPATT);
				String baseSpDef = pokemon
						.get(PokeContract.PokemonBaseStats.BASE_SPDEF);
				String baseSpeed = pokemon
						.get(PokeContract.PokemonBaseStats.BASE_SPEED);
				pokemonValues.put(PokeContract.PokemonName.NUMBER, number);
				pokemonStatsValues.put(PokeContract.PokemonName.NUMBER, number);
				type1Values.put(PokeContract.PokemonName.NUMBER, number);
				type2Values.put(PokeContract.PokemonName.NUMBER, number);
				ability1Values.put(PokeContract.PokemonName.NUMBER, number);
				ability2Values.put(PokeContract.PokemonName.NUMBER, number);
				abilityDWValues.put(PokeContract.PokemonName.NUMBER, number);
				pokemonValues.put(PokeContract.PokemonName.FORM, form);
				pokemonStatsValues.put(PokeContract.PokemonName.FORM, form);
				type1Values.put(PokeContract.PokemonName.FORM, form);
				type2Values.put(PokeContract.PokemonName.FORM, form);
				ability1Values.put(PokeContract.PokemonName.FORM, form);
				ability2Values.put(PokeContract.PokemonName.FORM, form);
				abilityDWValues.put(PokeContract.PokemonName.FORM, form);
				pokemonValues.put(PokeContract.PokemonName.NAME, name);
				pokemonStatsValues.put(PokeContract.PokemonBaseStats.BASE_HP,
						baseHP);
				pokemonStatsValues.put(PokeContract.PokemonBaseStats.BASE_ATT,
						baseAtt);
				pokemonStatsValues.put(PokeContract.PokemonBaseStats.BASE_DEF,
						baseDef);
				pokemonStatsValues.put(
						PokeContract.PokemonBaseStats.BASE_SPATT, baseSpAtt);
				pokemonStatsValues.put(
						PokeContract.PokemonBaseStats.BASE_SPDEF, baseSpDef);
				pokemonStatsValues.put(
						PokeContract.PokemonBaseStats.BASE_SPEED, baseSpeed);
				type1Values.put(PokeContract.PokemonType1.TYPE, type1);
				type2Values.put(PokeContract.PokemonType2.TYPE, type2);
				ability1Values.put(PokeContract.PokemonAbility1.ABILITY_1,
						ability1);
				ability2Values.put(PokeContract.PokemonAbility2.ABILITY_2,
						ability2);
				abilityDWValues.put(PokeContract.PokemonAbilityDW.ABILITY_DW,
						abilityDW);
				pokemons[i] = pokemonValues;
				stats[i] = pokemonStatsValues;
				type1s[i] = type1Values;
				type2s[i] = type2Values;
				ability1s[i] = ability1Values;
				ability2s[i] = ability2Values;
				abilitydws[i] = abilityDWValues;
			}

			mContext.getContentResolver().bulkInsert(
					PokeContract.PokemonName.CONTENT_POKEMON_NAME, pokemons);
			mContext.getContentResolver().bulkInsert(
					PokeContract.PokemonBaseStats.CONTENT_POKEMON_BASE_STATS,
					stats);
			mContext.getContentResolver().bulkInsert(
					PokeContract.PokemonType1.CONTENT_POKEMON_TYPE_1, type1s);
			mContext.getContentResolver().bulkInsert(
					PokeContract.PokemonType2.CONTENT_POKEMON_TYPE_2, type2s);
			mContext.getContentResolver().bulkInsert(
					PokeContract.PokemonAbility1.CONTENT_POKEMON_ABILITY_1,
					ability1s);
			mContext.getContentResolver().bulkInsert(
					PokeContract.PokemonAbility2.CONTENT_POKEMON_ABILITY_2,
					ability2s);
			mContext.getContentResolver().bulkInsert(
					PokeContract.PokemonAbilityDW.CONTENT_POKEMON_ABILITY_DW,
					abilitydws);
		}

		public void loadNatures() {
			ArrayList<String> elements = new ArrayList<String>();
			elements.add(PokeContract.Natures.NAME);

			ArrayList<HashMap<String, String>> result = DBReader.readDB(
					getResources().openRawResource(R.raw.natures), elements);

			for (int i = 0; i < result.size(); i++) {
				ContentValues values = new ContentValues();
				HashMap<String, String> nature = result.get(i);
				for (int j = 0; j < nature.size(); j++) {
					values.put(elements.get(j), nature.get(elements.get(j)));
				}
				mContext.getContentResolver().insert(
						PokeContract.Natures.CONTENT_NATURE, values);
			}
		}

		public void loadAbilities() {
			ArrayList<String> elements = new ArrayList<String>();
			elements.add(PokeContract.Abilities.ID);
			elements.add(PokeContract.Abilities.NAME);
			elements.add(PokeContract.Abilities.DESCRIPTION);

			ArrayList<HashMap<String, String>> result = DBReader.readDB(
					getResources().openRawResource(R.raw.abilities), elements);

			for (int i = 0; i < result.size(); i++) {
				ContentValues values = new ContentValues();
				HashMap<String, String> ability = result.get(i);
				for (int j = 0; j < ability.size(); j++) {
					values.put(elements.get(j), ability.get(elements.get(j)));
				}
				mContext.getContentResolver().insert(
						PokeContract.Abilities.CONTENT_ABILITY, values);
			}
		}
	}
}
