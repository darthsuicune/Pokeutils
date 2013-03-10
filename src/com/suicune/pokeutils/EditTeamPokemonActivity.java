package com.suicune.pokeutils;

import android.content.res.Configuration;
import android.os.Bundle;

import com.suicune.pokeutils.compat.ActionBarActivity;
import com.suicune.pokeutils.fragments.EditTeamPokemonFragment;

public class EditTeamPokemonActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			this.finish();
		}

		setContentView(R.layout.edit_team_pokemon);
		EditTeamPokemonFragment fragment = new EditTeamPokemonFragment();
		getSupportFragmentManager().beginTransaction()
				.add(R.id.edit_team_pokemon_container, fragment).commit();
	}
}
