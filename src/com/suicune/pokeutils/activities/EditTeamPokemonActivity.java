package com.suicune.pokeutils.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.fragments.EditTeamPokemonFragment;
import com.suicune.pokeutils.fragments.EditTeamPokemonFragment.EditTeamPokemonCallback;
import com.suicune.pokeutils.fragments.TeamBuilderFragment;

public class EditTeamPokemonActivity extends FragmentActivity implements
		EditTeamPokemonCallback {

	EditTeamAdapter mEditTeamAdapter;
	ViewPager mViewPager;

	public static final String EXTRA_TEAM_NUMBER = "teamNumber";
	public static final String EXTRA_TEAM = "team";

	Intent mResult;

	int mTeamNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_team_pokemon);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		preparePager();

//		if (savedInstanceState != null) {
//			return;
//		}
		if (getIntent().getExtras() == null) {
			return;
		}

		if (getIntent().getExtras().containsKey(EXTRA_TEAM_NUMBER)) {
			mTeamNumber = getIntent().getIntExtra(EXTRA_TEAM_NUMBER, 0);
		}
		if (getIntent().getExtras().containsKey(EXTRA_TEAM)) {
			mResult = new Intent();
			mResult.putExtra(TeamBuilderFragment.ARGUMENT_TEAM, getIntent()
					.getBundleExtra(EXTRA_TEAM));
		}
		
	}

	private void preparePager() {
		mEditTeamAdapter = new EditTeamAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.edit_team_pokemon_container);
		mViewPager.setOffscreenPageLimit(5);
		mViewPager.setAdapter(mEditTeamAdapter);
		mViewPager.setCurrentItem(mTeamNumber);
	}

	@Override
	public void registerPokemon(Bundle pokemon) {
		if(mResult == null){
			mResult = new Intent();
		}
		Bundle team;
		if(mResult.getExtras() != null && mResult.getExtras().containsKey(TeamBuilderFragment.ARGUMENT_TEAM)){
			team = mResult.getExtras().getBundle(
					TeamBuilderFragment.ARGUMENT_TEAM);
		} else {
			team = new Bundle();
		}
				
		
		team.putBundle(Integer.toString(mTeamNumber), pokemon);
		mResult.putExtra(TeamBuilderFragment.ARGUMENT_TEAM, team);
		setResult(RESULT_OK, mResult);
	}

	public class EditTeamAdapter extends FragmentPagerAdapter {

		public EditTeamAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = Fragment.instantiate(
					EditTeamPokemonActivity.this,
					EditTeamPokemonFragment.class.getName());
			fragment.setHasOptionsMenu(true);
			if ((getIntent().getExtras() != null)
					&& (getIntent().getExtras().containsKey(EXTRA_TEAM))
					&& (getIntent().getBundleExtra(EXTRA_TEAM)
							.containsKey(Integer.toString(position)))) {
				fragment.setArguments(getIntent().getBundleExtra(EXTRA_TEAM)
						.getBundle(Integer.toString(position)));

			}
			return fragment;
		}

		@Override
		public int getCount() {
			// We have just 6 pokemon on the list
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.pokemon_1) + " ("
						+ getString(R.string.lead) + ")";
			case 1:
				return getString(R.string.pokemon_2);
			case 2:
				return getString(R.string.pokemon_3);
			case 3:
				return getString(R.string.pokemon_4);
			case 4:
				return getString(R.string.pokemon_5);
			case 5:
				return getString(R.string.pokemon_6);
			default:
				return super.getPageTitle(position);
			}

		}

	}
}