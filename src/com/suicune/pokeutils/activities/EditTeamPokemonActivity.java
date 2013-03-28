package com.suicune.pokeutils.activities;

import android.os.Bundle;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.compat.ActionBarActivity;
import com.suicune.pokeutils.fragments.EditTeamPokemonFragment;

public class EditTeamPokemonActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.edit_team_pokemon);
		if(savedInstanceState != null){
			return;
		}
		EditTeamPokemonFragment fragment = new EditTeamPokemonFragment();
		fragment.setHasOptionsMenu(true);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.edit_team_pokemon_container, fragment).commit();
	}
}
