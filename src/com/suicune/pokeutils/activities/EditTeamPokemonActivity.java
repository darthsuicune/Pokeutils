package com.suicune.pokeutils.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.fragments.EditTeamPokemonFragment;
import com.suicune.pokeutils.fragments.TeamBuilderFragment;
import com.suicune.pokeutils.fragments.EditTeamPokemonFragment.EditTeamPokemonCallback;

public class EditTeamPokemonActivity extends Activity implements
		EditTeamPokemonCallback {
	public static final String EXTRA_TEAM_NUMBER = "teamNumber";
	public static final String EXTRA_POKEMON = "pokemon";

	Intent mResult;

	int mTeamNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_team_pokemon);
		mResult = new Intent();
		Bundle team = new Bundle();
		mResult.putExtra(TeamBuilderFragment.ARGUMENT_TEAM, team);
		if (savedInstanceState != null) {
			return;
		}
		if (getIntent().getExtras() == null) {
			return;
		}
		if (getIntent().getExtras().containsKey(EXTRA_TEAM_NUMBER)) {
			mTeamNumber = getIntent().getIntExtra(EXTRA_TEAM_NUMBER, 0);
		}
		Fragment fragment = Fragment.instantiate(this,
				EditTeamPokemonFragment.class.getName());
		if (getIntent().getExtras().containsKey(EXTRA_POKEMON)) {
			Bundle args = new Bundle();
			args.putBundle(EXTRA_POKEMON,
					getIntent().getBundleExtra(EXTRA_POKEMON));
			fragment.setArguments(args);
		}
		fragment.setHasOptionsMenu(true);
		getFragmentManager().beginTransaction()
				.add(R.id.edit_team_pokemon_container, fragment).commit();
	}

	@Override
	public void registerPokemon(Bundle pokemon) {
		Bundle team = mResult.getExtras().getBundle(
				TeamBuilderFragment.ARGUMENT_TEAM);
		team.putBundle(Integer.toString(mTeamNumber), pokemon);
		setResult(RESULT_OK, mResult);
	}
}