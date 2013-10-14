package com.suicune.pokeutils.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import com.suicune.pokeutils.R;
import com.suicune.pokeutils.ui.fragments.EditTeamPokemonFragment;
import com.suicune.pokeutils.ui.fragments.EditTeamPokemonFragment.EditTeamPokemonCallback;
import com.suicune.pokeutils.ui.fragments.TeamBuilderFragment;

public class EditTeamPokemonActivity extends ActionBarActivity implements
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
		getSupportFragmentManager().beginTransaction()
				.add(R.id.edit_team_pokemon_container, fragment).commit();
	}

	@Override
	public void registerPokemon(Bundle pokemon) {
		Bundle team = mResult.getExtras().getBundle(
				TeamBuilderFragment.ARGUMENT_TEAM);
		team.putBundle(Integer.toString(mTeamNumber), pokemon);
		mResult.putExtra(TeamBuilderFragment.ARGUMENT_TEAM, team);
		setResult(RESULT_OK, mResult);
	}
}